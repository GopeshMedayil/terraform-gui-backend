CREATE TABLE projects (

    id UUID PRIMARY KEY,

    project_name VARCHAR(150) NOT NULL,

    project_code VARCHAR(50) NOT NULL UNIQUE,

    description TEXT,

    cloud_provider VARCHAR(30) NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_by VARCHAR(100),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL,

    deleted BOOLEAN DEFAULT FALSE

);