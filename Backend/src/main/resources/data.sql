INSERT INTO Areas (area_state, area_auduser, area_description) VALUES
(1, 1002, 'Finanzas'),
(1, 1003, 'Tecnología'),
(0, 1004, 'Inactiva');

INSERT INTO Employees (employee_name, employee_surname, employee_document, employee_state, employee_auduser, area_id)
VALUES
('John', 'Doe', 'DOC123456', 1, 1001, 1),
('Jane', 'Smith', 'DOC654321', 1, 1002, 2),
('Carlos', 'Martinez', 'DOC987654', 1, 1003, 1),
('Lucía', 'Gómez', 'DOC321987', 0, 1004, 1),
('Emily', 'Johnson', 'DOC567890', 1, 1005, 2);

INSERT INTO Roles (role_description, role_state, role_auduser)
VALUES ('ADMIN', 1, 1),
       ('USER', 1, 1),
       ('GUEST', 1, 1);

INSERT INTO Employee_Shifts (employee_id, shift_day, shift_entry, shift_exit, shift_duration, shift_state, shift_auduser)
VALUES (1, 'L', '08:00:00', '16:00:00', 8, 1, 1001),
       (1, 'M', '08:00:00', '16:00:00', 8, 1, 1001),
       (2, 'X', '09:00:00', '17:00:00', 8, 1, 1002),
       (3, 'J', '07:30:00', '15:30:00', 8, 1, 1003),
       (3, 'V', '07:30:00', '15:30:00', 8, 1, 1003),
       (4, 'L', '10:00:00', '18:00:00', 8, 0, 1004),  -- Inactive
       (5, 'S', '08:00:00', '12:00:00', 4, 1, 1005);

INSERT INTO Employee_Times (employee_id, time_day, time_type, time_ontime, time_state, time_auduser)
VALUES
(1, '2025-05-13T08:00:00', 'E', TRUE, 1,1001),
(1, '2025-05-13T16:00:00', 'S', TRUE, 1,1001),
(2, '2025-05-13T09:05:00', 'E', FALSE, 1,1001),
(2, '2025-05-13T17:00:00', 'S', TRUE, 1,1001),
(3, '2025-05-13T07:30:00', 'E', TRUE, 1,1001),
(3, '2025-05-13T15:20:00', 'S', FALSE, 1,1001),
(5, '2025-05-17T08:02:00', 'E', FALSE, 1,1001),
(5, '2025-05-17T12:01:00', 'S', FALSE, 1,1001);
