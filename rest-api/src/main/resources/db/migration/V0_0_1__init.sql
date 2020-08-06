create table pull_request
(
    id         bigint      not null primary key,
    title      varchar     not null,
    url        varchar     not null,
    created_at timestamptz not null,
    closed_at  timestamptz not null,
    lifetime   bigint      not null,
    merged_at  timestamptz
);
