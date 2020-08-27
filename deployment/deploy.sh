#!/bin/bash
set -euo pipefail
IFS=$'\n\t'

if ! type -P gh 1>/dev/null; then
  echo "This script requires the github cli to be installed. (https://github.com/cli/cli)"
  exit 1
fi

if ! type -P jq 1>/dev/null; then
  echo "This script requires jq to be installed. (https://github.com/stedolan/jq)"
  exit 1
fi

if ! type -P kustomize 1>/dev/null; then
  echo "This script requires kustomize to be installed. (https://github.com/kubernetes-sigs/kustomize)"
  exit 1
fi

if ! type -P kubectl 1>/dev/null; then
  echo "This script requires kubectl to be installed. (https://kubernetes.io/de/docs/tasks/tools/install-kubectl/)"
  exit 1
fi

declare -r version=$(gh api repos/smartsquaregmbh/smidle/releases | jq -r 'first(.[] | select(.draft==false) | select(.prerelease==false)) | .tag_name')

if [[ -z "$version" ]]
then
  echo -e "\033[0;31mThere is no production release yet. :(\033[0m"
  exit 1
fi

read -p "Do you really want to deploy version $version to production? (y/n) " RESP
if [ "$RESP" = "y" ]; then
  kustomize edit set image docker.pkg.github.com/smartsquaregmbh/smidle/smidle-rest-api=docker.pkg.github.com/smartsquaregmbh/smidle/smidle-rest-api:$version
  kustomize edit set image docker.pkg.github.com/smartsquaregmbh/smidle/smidle-frontend=docker.pkg.github.com/smartsquaregmbh/smidle/smidle-frontend:$version

  kustomize build . | kubectl apply --cluster='leonies-cluster' -f -

  git checkout HEAD kustomization.yaml --quiet

  echo ""
  echo -e "\033[1;32mDone! I got the current deployment status - just for you:\033[0m"
  kubectl get deployment --cluster='leonies-cluster' --all-namespaces
else
  echo "Phew! That was close..."
fi