CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE files (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL,
    format VARCHAR(50),
    upload_date TIMESTAMP NOT NULL
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    file_id UUID,

    CONSTRAINT fk_file_user
        FOREIGN KEY (file_id)
        REFERENCES files(id)
);

CREATE TABLE projects (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- Cria um UUID automaticamente
    title VARCHAR(255) NOT NULL,
    admin_id UUID,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_admin
        FOREIGN KEY (admin_id)
        REFERENCES users(id)
);

CREATE TABLE tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(), -- UUID gerado automaticamente
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50),
    type VARCHAR(50),
    file_id UUID, -- Referência a tabela files
    project_id UUID, -- Adicionada a coluna project_id

    CONSTRAINT fk_file
        FOREIGN KEY (file_id)
        REFERENCES files(id),

    CONSTRAINT fk_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
        ON DELETE CASCADE
);

CREATE TABLE task_checklist (
    task_id UUID NOT NULL,
    title VARCHAR(255),
    completed BOOLEAN DEFAULT FALSE,

    PRIMARY KEY (task_id, title),

    CONSTRAINT fk_task_checklist
    FOREIGN KEY (task_id)
    REFERENCES tasks(id)
    ON DELETE CASCADE
);

CREATE TABLE task_users (
    task_id UUID NOT NULL,
    user_id UUID NOT NULL,

    PRIMARY KEY (task_id, user_id),

    CONSTRAINT fk_task
    FOREIGN KEY (task_id)
    REFERENCES tasks(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);

CREATE TABLE project_members (
    project_id UUID NOT NULL,
    user_id UUID NOT NULL,

    PRIMARY KEY (project_id, user_id),

    CONSTRAINT fk_project_member
    FOREIGN KEY (project_id)
    REFERENCES projects(id)
    ON DELETE CASCADE,

    CONSTRAINT fk_user_member
    FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
);
