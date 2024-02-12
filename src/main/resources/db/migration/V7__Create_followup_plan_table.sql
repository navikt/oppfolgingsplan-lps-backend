DROP TABLE OPPFOLGINGSPLAN_LPS_V1;

CREATE TABLE OPPFOLGINGSPLAN_LPS_V1
(
    uuid                                             UUID PRIMARY KEY,
    organization_number                              VARCHAR(9)  NOT NULL,
    employee_identification_number                   VARCHAR(11) NOT NULL,
    typical_workday                                  TEXT        NOT NULL,
    tasks_that_can_still_be_done                     TEXT        NOT NULL,
    tasks_that_can_not_be_done                       TEXT        NOT NULL,
    previous_facilitation                            TEXT        NOT NULL,
    planned_facilitation                             TEXT        NOT NULL,
    other_facilitation_options                       TEXT,
    follow_up                                        TEXT        NOT NULL,
    evaluation_date                                  DATE        NOT NULL,
    send_plan_to_nav                                 BOOLEAN     NOT NULL,
    needs_help_from_nav                              BOOLEAN,
    needs_help_from_nav_description                  TEXT,
    send_plan_to_general_practitioner                BOOLEAN     NOT NULL,
    message_to_general_practitioner                  TEXT,
    additional_information                           TEXT,
    contact_person_full_name                         TEXT        NOT NULL,
    contact_person_phone_number                      TEXT        NOT NULL,
    employee_has_contributed_to_plan                 BOOLEAN     NOT NULL,
    employee_has_not_contributed_to_plan_description TEXT,
    pdf                                              BYTEA,
    sent_to_nav_at                                   TIMESTAMP,
    sent_to_general_practitioner_at                  TIMESTAMP,
    send_to_general_practitioner_retry_count         INTEGER     NOT NULL DEFAULT 0,
    journalpost_id                                   VARCHAR(20),
    created_at                                       TIMESTAMP   NOT NULL,
    last_updated_at                                  TIMESTAMP   NOT NULL
);
