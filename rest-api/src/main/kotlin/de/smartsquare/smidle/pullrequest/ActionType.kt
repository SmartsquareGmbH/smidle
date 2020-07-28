package de.smartsquare.smidle.pullrequest

enum class ActionType {
    OPENED,
    EDITED,
    CLOSED,
    ASSIGNED,
    UNASSIGNED,
    REVIEW_REQUESTED,
    REVIEW_REQUEST_REMOVED,
    READY_FOR_REVIEW,
    LABELED,
    UNLABELED,
    SYNCHRONIZE,
    LOCKED,
    UNLOCKED,
    REOPENED
}
