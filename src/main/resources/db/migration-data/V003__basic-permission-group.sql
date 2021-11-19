/* 
    SysRole {
        USER, ADM, MNG;
    }
 */
insert into security.role(id, name, description, sys_role) values 
(1, 'System administrator', 'System administrator', 1), 
(2, 'Business manager', 'Business manager', 2),
(3, 'User', 'Regular user', 0) ;

