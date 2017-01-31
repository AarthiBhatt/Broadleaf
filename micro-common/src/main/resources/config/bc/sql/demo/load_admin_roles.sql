--
-- Creates the out of box admin roles and associates them with out of box permissions
--

--
-- Create BLC ROLES (These roles are required for the admin)
--
INSERT INTO BLC_ADMIN_ROLE (ADMIN_ROLE_ID, DESCRIPTION, NAME, DATE_UPDATED) VALUES (-1,'Admin Master Access','ROLE_ADMIN', CURRENT_TIMESTAMP);