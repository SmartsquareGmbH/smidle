export default function humanreadableLifetime(lifetimeInMinutes) {
  const days = Math.floor(lifetimeInMinutes / 60 / 24)
  const hours = Math.floor((lifetimeInMinutes / 60) % 24)
  const minutes = lifetimeInMinutes - days * 24 * 60 - hours * 60

  const lifetime = []
  if (days === 1) {
    lifetime.push("1 day")
  } else if (days >= 1) {
    lifetime.push(`${days} days`)
  }
  if (hours === 1) {
    lifetime.push("1 hour")
  } else if (hours >= 1) {
    lifetime.push(`${hours} hours`)
  }
  if (minutes === 1) {
    lifetime.push("1 minute")
  } else if (minutes >= 1) {
    lifetime.push(`${minutes} minutes`)
  }
  return lifetime.join(" ")
}
