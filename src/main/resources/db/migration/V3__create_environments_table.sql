CREATE TABLE environments (
    id UUID PRIMARY KEY,
    project_id UUID NOT NULL,
    environment_name VARCHAR(100) NOT NULL,
    environment_code VARCHAR(30) NOT NULL,
    description TEXT,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_environment_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id),

    CONSTRAINT uk_environment_project_code
        UNIQUE (project_id, environment_code)
);