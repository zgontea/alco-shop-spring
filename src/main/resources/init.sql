insert into users (firstname, lastname, email, password, phone, admin) values
    ('Mr.', 'Admin', 'admin@alcoshop.pl', '$2a$10$32IDW.SWwZ64BTvaxy02oONQFoa4SmhcKAAUuYxm77ena7uS9lEpO', '509763578', true);

insert into categories (id, name) values
    (1, 'Wódki'),
    (2, 'Wina'),
    (3, 'Piwa'),
    (4, 'Whiskey'),
    (5, 'Likiery');

insert into products (name, image, unit_price, description, size, concentration, category_id) values
    ('Absolut Lime', 'absolut-lime.jpg', 54.99, 'Absolut o smaku limonki', 1000, 40, 1),
    ('Absolut Grapefruit', 'absolut-grapefruit.jpg', 53.99, 'Absolut o smaku grejfrutowym', 1000, 40, 1),
    ('Absolut Raspberry', 'absolut-raspberri.jpg', 52.99, 'Absolut o smaku malinowym', 1000, 40, 1),
    ('Absolut', 'absolut-vodka.jpg', 57.99, 'Wódka czysta Absolut', 1000, 40, 1),
    ('Absolut Watermelon', 'absolut-watermelon.jpg', 52.99, 'Absolut o smaku arbuzowym', 1000, 40, 1),
    ('Los Pinguinos', 'los-pinguinos.jpg', 82.99, 'Wino z pingwina', 750, 11, 2),
    ('Fresco', 'fresco.jpg', 22.99, 'Różowe pół słodkie', 750, 10, 2),
    ('El Sol', 'el-sol.jpg', 32.99, 'Hiszpańskie różowe pół słodkie', 750, 12, 2),
    ('Somersby Pina Colada', 'piwo-somersby-pina-colada.jpg', 4.99, 'Smaki lata', 400, 4.5, 3);
