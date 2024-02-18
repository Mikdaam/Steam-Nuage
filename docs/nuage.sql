DROP TABLE IF EXISTS Entreprise CASCADE;
DROP TABLE IF EXISTS Jeux CASCADE;
DROP TABLE IF EXISTS Joueur CASCADE;
DROP TABLE IF EXISTS Succes CASCADE;
DROP TABLE IF EXISTS Appartient CASCADE;
DROP TABLE IF EXISTS Avis CASCADE;
DROP TABLE IF EXISTS Est_ami_e CASCADE;
DROP TABLE IF EXISTS Partager CASCADE;
DROP TABLE IF EXISTS Debloque CASCADE;
DROP TABLE IF EXISTS Achat CASCADE;

-- Create necessary tables

-- Entreprise(id_entreprise, nom, pays)
CREATE TABLE Entreprise
(
    id_entreprise serial primary key,
    nom           varchar(255) NOT NULL,
    pays          varchar(100)
);

-- Jeux(id_jeux, titre, date_sortie, prix, age_requis, description_jeux, id_editeur, id_developpeur)
CREATE TABLE Jeux
(
    id_jeux          serial primary key,
    titre            varchar(255) NOT NULL,
    date_sortie      date         not null,
    prix             int,
    age_requis       int          NOT NULL,
    description_jeux text,
    id_editeur       int references Entreprise (id_entreprise),
    id_developpeur   int references Entreprise (id_entreprise)
);
-- Ne connaissant pas la taille moyenne d'une description d'un jeu vidéo, 
-- nous avons choisit le type "text" pour le champ "description_jeux" permettant de resérver de la place dynamiquement.
-- De plus cela permet de ne pas réserver de l'espace en trop.

-- Joueur(pseudo, passwd, nom, adress_mail, date_naissance, monnaie)
CREATE TABLE Joueur
(
    pseudo         varchar(100) primary key,
    passwd         varchar(255),
    nom            varchar(100),
    adress_mail    varchar(100) not null,
    date_naissance date,
    monnaie        int default 0
);

-- Succes(num_succes, intitule, conditions, type_succes)
CREATE TABLE Succes
(
    num_succes serial primary key,
    intitule   varchar(255),
    conditions text,
    id_jeux    int references Jeux (id_jeux)
);

-- Appartient (id_jeux, type_genre)
CREATE TABLE Appartient
(
    id_jeux    int references Jeux (id_jeux),
    type_genre varchar(100),
    primary key (id_jeux, type_genre)
);

CREATE TABLE Est_ami_e
(
    pseudo_joueur_1 varchar(100) references Joueur (pseudo),
    pseudo_joueur_2 varchar(100) references Joueur (pseudo),
    primary key (pseudo_joueur_1, pseudo_joueur_2)
);

-- Achat(id_jeux, pseudo)
CREATE TABLE Achat
(
    id_jeux int references Jeux (id_jeux),
    pseudo  varchar(100) references Joueur (pseudo),
    primary key (id_jeux, pseudo)
);

CREATE TABLE Avis
(
    pseudo      VARCHAR(100) references Joueur (pseudo),
    id_jeux     INTEGER references Jeux (id_jeux),
    note        INTEGER,
    commentaire VARCHAR(500),
    primary key (pseudo, id_jeux)
);

-- Partager(id_jeux, pseudo_joueur_1, pseudo_joueur_2)
CREATE TABLE Partager
(
    id_jeux         int references Jeux (id_jeux),
    pseudo_joueur_1 varchar(100) references Joueur (pseudo),
    pseudo_joueur_2 varchar(100) references Joueur (pseudo),
    primary key (id_jeux, pseudo_joueur_1, pseudo_joueur_2),
    constraint uniquePartage unique (id_jeux, pseudo_joueur_1)
);
-- TODO: Test à faire

-- Debloque(num_succes, pseudo, date_deblocage)
CREATE TABLE Debloque
(
    num_succes     int references Succes (num_succes),
    pseudo         varchar(100) references Joueur (pseudo),
    date_deblocage date not null,
    primary key (num_succes, pseudo)
);

-- remplir la table Entreprise
INSERT INTO Entreprise(nom, pays)
VALUES ('Breanna Blair', 'Spain'),
       ('Axel Vaughn', 'New Zealand'),
       ('Hedwig Solis', 'India'),
       ('Colton Cobb', 'Mexico'),
       ('Gregory Small', 'Germany'),
       ('Blizzard', 'France'),
       ('Ubisoft', 'France'),
       ('Nitendo', 'Japon');

-- remplir la table Jeux
INSERT INTO Jeux(titre, date_sortie, prix, age_requis, description_jeux, id_editeur, id_developpeur)
VALUES ('Ornare Placerat', '2021-07-06', 90716, 12,
        'pharetra nibh. Aliquam ornare, libero at auctor ullamcorper, nisl arcu iaculis enim, sit amet ornare lectus justo eu arcu. Morbi sit amet massa. Quisque porttitor eros',
        8, 8),
       ('Leo. Morbi', '2022-01-20', 28086, 10,
        'amet, consectetuer adipiscing elit. Aliquam auctor, velit eget laoreet posuere,', 1, 5),
       ('Nec luctus', '2021-11-20', 94090, 16,
        'ut, sem. Nulla interdum. Curabitur dictum. Phasellus in felis. Nulla tempor augue ac ipsum. Phasellus vitae mauris sit amet lorem semper auctor. Mauris vel turpis. Aliquam adipiscing lobortis risus. In mi pede, nonummy ut, molestie in, tempus eu, ligula. Aenean euismod mauris eu elit. Nulla facilisi. Sed neque. Sed eget lacus. Mauris non dui nec urna suscipit nonummy. Fusce',
        2, 4),
       ('Magna nec', '2022-04-27', 93292, 8,
        'imperdiet, erat nonummy ultricies ornare, elit elit fermentum risus, at fringilla purus mauris a nunc. In at pede. Cras vulputate velit eu sem. Pellentesque ut ipsum ac mi eleifend egestas. Sed pharetra, felis eget varius ultrices, mauris ipsum porta elit, a feugiat tellus lorem eu metus. In lorem. Donec elementum, lorem ut aliquam iaculis, lacus pede sagittis augue, eu tempor erat neque non quam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam fringilla cursus purus. Nullam scelerisque neque sed sem',
        1, 3),
       ('Donec', '2021-09-19', 29983, 5,
        'nec orci. Donec nibh. Quisque nonummy ipsum non arcu. Vivamus sit amet risus. Donec egestas. Aliquam nec enim. Nunc ut erat. Sed nunc est, mollis non, cursus non, egestas',
        7, 7);

-- remplir la table joueur
INSERT INTO Joueur(pseudo, passwd, nom, adress_mail, date_naissance, monnaie)
VALUES ('Harrison', 'I4.U$=Y k<', 'Amanda', 'Leo@fermentum.gov', '2000-06-22', 1183),
       ('Craft', '2c&J} a`ZU', 'Raymond', 'Kibo@commodo.us', '2000-11-30', 2531),
       ('Sanford', 'qp*?FW /W2', 'Derek', 'Susan@hymenaeos.edu', '2001-05-10', 4022),
       ('Vaughn', '2#.OUxZ8E8', 'Harriet', 'Aidan@metus.org', '2001-10-18', 3782),
       ('Coffey', 'g+L^/uSagW', 'Unity', 'Byron@velit.net', '2002-03-28', 1647),
       ('Kinney', '_$9a# /3_r', 'Regina', 'Jolene@Nunc.us', '2002-09-05', 2659),
       ('Goodwin', 'nD;<(6)ra;', 'Astra', 'Ebony@luctus.net', '2003-02-13', 34),
       ('Huff', 'soQuk:!g?[', 'Shannon', 'Alma@dolor.gov', '2003-07-24', 3600),
       ('Guerrero', 'Lfo[~7$:)_', 'Brittany', 'Phyllis@dolor.net', '2004-01-01', 547),
       ('Mcneil', '&pV0X^rj)S', 'Gemma', 'Kane@tellus.edu', '2004-06-10', 2853),
       ('Dudle', 'LoYlKnZ^0o', 'Pascale', 'Dustin@iaculis.org', '2004-11-18', 4048),
       ('Holcomb', 'UZ%v0b09r+', 'Rooney', 'Roth@pretium.us', '2005-04-28', 144),
       ('Martinez', 'M*o;Mj1R9', 'Zahir', 'Gannon@Ut.us', '2005-10-06', 144),
       ('Bright', 'qxj$[N TAH', 'Isaac', 'Quinn@Praesent.edu', '2006-03-16', 1290),
       ('Dudley', 'DXBC^77.$/', 'Mia', 'Alea@porttitor.org', '2006-08-24', 294),
       ('Ochoa', '!/%!b-@#yt', 'Debra', 'Murphy@ullamcorper.com', '2007-02-01', 2218),
       ('Griffin', 'VHP-^(9gvO', 'Hayden', 'Lawrence@netus.net', '2007-07-12', 2814),
       ('Adkins', '*Nww`2PG&a', 'Gregory', 'Samuel@accumsan.com', '2007-12-20', 4591),
       ('Moses', 'WB9WD/Q\1-', 'Jada', 'Giacomo@suscipit.org', '2008-05-29', 3293),
       ('Ratliff', 'tsC3kC.+8Q', 'Darryl', 'Dolan@elit.us', '2008-11-06', 4578),
       ('Boone', 'B4q]gGo)Un', 'Adrian', 'Jamalia@viverra.gov', '2009-04-16', 2835),
       ('Mcclain', 'Xd[:}b? 40', 'Stuart', 'Erasmus@ultricies.net', '2009-09-24', 4244),
       ('Gay', 'N35@l2Kw]2', 'Aaron', 'August@bibendum.net', '2010-03-04', 649),
       ('Walton', '9A.Xa(/VmZ', 'Patience', 'Uriah@Curae.org', '2010-08-12', 3752),
       ('Kennedy', 'ew)lfCeOHD', 'Candice', 'September@Fusce.org', '2011-01-20', 3235),
       ('Hampton', 'OMaSo7uY#f', 'Samuel', 'Jelani@iaculis.net', '2011-06-30', 4397),
       ('Wolfe', 'jqWB<$-}IH', 'Ora', 'Constance@velit.net', '2011-12-08', 1452),
       ('Burks', '3>/I32hSK;', 'Echo', 'Wendy@molestie.org', '2012-05-17', 1488),
       ('Christian', 'JGqUzfP4rY', 'Claudia', 'Arthur@congue.edu', '2012-10-25', 3809),
       ('Charles', '<&1Y6Ctpy<', 'Kyra', 'Quynn@tempor.gov', '2013-04-04', 4124),
       ('Thompson', 't*jsjFU6fR', 'Kristen', 'Rudyard@ac.net', '2013-09-12', 4869),
       ('Rose', 'bgA#mkO.%<', 'Adele', 'Rose@suscipit.edu', '2014-02-20', 3474),
       ('Hammond', 'L5=JA.ryWY', 'Quon', 'Adele@odio.net', '2014-07-31', 2473),
       ('Henry', 'G~pI-$t0W)', 'Evan', 'Hop@fringilla.com', '2015-01-08', 4000),
       ('Joyce', '^Gt3H!\SK[', 'Orlando', 'Stella@nunc.edu', '2015-06-18', 4306),
       ('Strong', '9q]Nbd`9*r', 'Preston', 'Venus@vel.us', '2015-11-26', 1074),
       ('Bradshaw', 'kD>uaW;Ymz', 'Nicholas', 'Lewis@euismod.edu', '2016-05-05', 4799),
       ('Russell', '197t>]ADzV', 'Lesley', 'Noah@morbi.edu', '2016-10-13', 3637),
       ('Riddle', 'r\K[yw%qtf', 'Hakeem', 'Belle@commodo.org', '2017-03-23', 1302),
       ('Estrada', 'p$h8Sw*t6V', 'Yeo', 'Jenette@Sed.org', '2017-08-31', 1706),
       ('Alford', '{WiwFNcsjx', 'Harlan', 'Fitzgerald@condimentum.edu', '2018-02-08', 2481),
       ('Carroll', 'eLJe8ocaP0', 'Natalie', 'Medge@pellentesque.org', '2018-07-19', 3464),
       ('Baxter', 'Aa)Hb+w@R4', 'Brenden', 'Moses@lobortis.us', '2018-12-27', 782),
       ('Snider', ']IFH8[ywL', 'Beau', 'Aileen@sagittis.net', '2019-06-06', 2978),
       ('Mcknight', '`a7^\:$a<P', 'Ali', 'Jasper@ultricies.gov', '2019-11-14', 4180),
       ('Ray', 'VnC(rPk1lh', 'Georgia', 'Christen@ultrices.net', '2020-04-23', 3633),
       ('Chen', '.3`U+oK.dh', 'Micah', 'Cecilia@metus.gov', '2020-10-01', 4598),
       ('Hubbard', 'K>Z.FffrE1', 'Ira', 'Sade@mus.us', '2021-03-11', 2422),
       ('Noble', 'VHG&]h48yM', 'Veda', 'Donna@metus.gov', '2021-08-19', 1640),
       ('Dodson', 'UT!I1%d5UW', 'Nicole', 'Aimee@enim.gov', '2022-01-27', 4462);

-- remplir la table succes
INSERT INTO Succes(intitule, conditions, id_jeux)
VALUES ('vestibulum et', 'sagittis Maecenas luctus iaculis magnis vel In sit turpis Curae libero Sed netus euismod', 2),
       ('netus imperdiet', 'posuere facilisi lorem gravida iaculis', 3),
       ('mollis justo', 'sed sit gravida sodales auctor lacus scelerisque faucibus', 5),
       ('condimentum euismod', 'erat arcu laoreet', 4),
       ('facilisis pharetra',
        'gravida libero viverra nisl Maecenas dapibus ullamcorper Lorem parturient laoreet malesuada Nunc congue hendrerit habitant varius Curabitur',
        1),
       ('posuere interdum',
        'habitant venenatis dictum Pellentesque fames justo condimentum cursus pede eu fringilla magnis sapien Curae ipsum nisi aptent',
        1),
       ('Sed nec', 'tristique elementum Maecenas vehicula', 5),
       ('nulla morbi',
        'in porta aptent tellus aliquet lorem fames lobortis cursus mattis eros sit risus scelerisque blandit nostra quam',
        4),
       ('viverra vitae', 'arcu Sed eget nec bibendum Curae', 2),
       ('leo vel', 'nec et tincidunt fringilla ante Quisque nascetur urna', 2),
       ('egestas morbi',
        'convallis vel erat Curae senectus tortor laoreet imperdiet vestibulum porttitor Nam quam in ligula nostra erat risus',
        4),
       ('Nam felis', 'orci pulvinar amet egestas porttitor sem nulla nulla interdum Quisque rutrum tristique mus', 2),
       ('nostra sed',
        'velit facilisi lobortis Nulla justo Donec urna nibh purus sociosqu mollis sagittis per sagittis dui sit', 2),
       ('Cum netus', 'laoreet', 1),
       ('pretium porta', 'odio Maecenas', 2),
       ('mauris porttitor',
        'lorem ac sem morbi Curae mi mauris rhoncus parturient ridiculus Pellentesque sapien viverra Donec nisi vestibulum sodales vestibulum Vestibulum ut',
        3),
       ('sodales Vestibulum', 'Duis per placerat facilisi eget dui', 3),
       ('fermentum tincidunt', 'Proin Maecenas', 4),
       ('molestie scelerisque',
        'justo dapibus ante tellus augue enim accumsan dis sem Fusce iaculis ac scelerisque In faucibus litora gravida neque congue',
        3),
       ('leo morbi', 'ipsum Nam venenatis id malesuada augue Aliquam', 1),
       ('leo mus', 'euismod elit Fusce mattis vel risus pellentesque Nulla vehicula ut ante mollis facilisis posuere',
        1),
       ('sollicitudin sollicitudin', 'mus Quisque ornare molestie posuere consequat odio Nullam orci Aenean', 5),
       ('bibendum nisi', 'congue scelerisque Fusce nisi sociis interdum ligula vitae fringilla congue', 5),
       ('mauris purus',
        'Integer per risus adipiscing nisi semper euismod diam In Fusce nonummy felis dictum dolor sem sollicitudin Cras',
        3),
       ('elementum penatibus',
        'accumsan egestas sem Maecenas inceptos nec convallis primis montes massa id morbi Quisque nostra pellentesque vulputate sociosqu',
        3),
       ('consectetuer per',
        'mauris sociosqu adipiscing odio sit tellus ridiculus sapien libero nibh mattis elementum torquent faucibus feugiat mattis natoque Vivamus',
        2),
       ('Praesent purus', 'tempus tempus eros parturient nonummy', 2),
       ('sem facilisis', 'pellentesque netus fringilla magnis lectus ac at Lorem convallis vel netus', 4),
       ('purus fermentum',
        'nonummy Vivamus per nascetur ad cursus montes interdum leo pellentesque penatibus diam ornare a', 4),
       ('nec condimentum', 'semper aliquet aliquam morbi montes Integer', 3),
       ('aliquam fames', 'porta condimentum felis torquent scelerisque Vivamus nec luctus mollis', 1),
       ('urna ac', 'Nulla Etiam metus massa fringilla dapibus Maecenas vestibulum vitae Cras', 4),
       ('risus tempus', 'Cum pretium fringilla fermentum Phasellus quis luctus', 4),
       ('dis natoque', 'ad risus Quisque Sed in laoreet Nunc justo cubilia eros leo elementum ipsum', 1),
       ('ligula egestas', 'aliquet parturient elementum mus conubia habitant ligula lacus scelerisque turpis', 2),
       ('facilisis bibendum', 'malesuada Nullam a ad Morbi lacus Aenean urna ullamcorper hymenaeos', 3),
       ('nisi Nunc', 'pulvinar hendrerit In Nunc', 2),
       ('bibendum est',
        'rutrum scelerisque a commodo pellentesque Etiam tristique accumsan a semper sem Cras massa Cum vehicula mus tellus Morbi dis',
        4),
       ('litora Fusce', 'risus suscipit pharetra fermentum dignissim Cum sapien ipsum ante natoque nulla bibendum', 3),
       ('torquent velit', 'Quisque odio montes porta nisl nostra ultrices primis sociis', 2),
       ('vehicula leo',
        'ullamcorper Curae inceptos sem nulla senectus sed senectus est quis ridiculus iaculis eros per ipsum Lorem id pulvinar facilisis',
        3),
       ('lobortis feugiat',
        'lorem gravida libero felis lorem senectus pulvinar dignissim taciti tempus mus sociis feugiat sit', 4),
       ('fermentum Ut',
        'Cum arcu aliquet pulvinar gravida imperdiet auctor nonummy ultricies at penatibus mollis Nullam convallis venenatis volutpat sed libero',
        3),
       ('interdum fermentum', 'Lorem condimentum pulvinar', 2),
       ('elementum condimentum',
        'hymenaeos Mauris diam montes porttitor nibh vehicula Praesent orci sed Cras ante Morbi imperdiet nostra', 4),
       ('imperdiet ac', 'Cum dolor urna aliquam iaculis Nunc morbi Quisque', 2),
       ('ut sollicitudin', 'purus interdum Duis Sed Class', 5),
       ('ornare nascetur', 'sociosqu pharetra risus Etiam eleifend', 2),
       ('mus mollis',
        'fermentum ante lacinia arcu tincidunt vehicula eleifend ligula Integer Praesent justo Proin Proin', 2),
       ('et velit', 'vel dapibus Class torquent fringilla venenatis Vestibulum risus Nullam ridiculus Class', 2);

-- remplir la table appartient
INSERT INTO Appartient(id_jeux, type_genre)
VALUES (1, 'action'),
       (5, 'aventure'),
       (2, 'fps'),
       (3, 'course'),
       (4, 'multijoueur'),
       (1, 'beat them all'),
       (3, 'plate-forme'),
       (3, 'simulation');

-- remplir la table Est_ami_e
INSERT INTO Est_ami_e(pseudo_joueur_1, pseudo_joueur_2)
VALUES ('Harrison', 'Craft'),
       ('Hammond', 'Strong'),
       ('Baxter', 'Carroll'),
       ('Mcknight', 'Thompson'),
       ('Noble', 'Kennedy');

-- remplir la table Achat
INSERT INTO Achat(id_jeux, pseudo)
VALUES (1, 'Harrison'),
       (5, 'Harrison'),
       (2, 'Hammond'),
       (4, 'Hammond'),
       (3, 'Joyce'),
       (3, 'Ratliff');

-- remplir la table Avis
INSERT INTO Avis(pseudo, id_jeux, note, commentaire)
VALUES ('Harrison', 1, 4, 'J aimerais mettre un commentaire long mais ....'),
       ('Harrison', 5, 5, 'Trop Nickel comme jeu'),
       ('Joyce', 3, 4, 'D habitude je ne donne pas de commentaire, maais ...'),
       ('Ratliff', 3, 5, 'C est mon jeu préfére du moment');

-- remplir la table Partager
INSERT INTO Partager(id_jeux, pseudo_joueur_1, pseudo_joueur_2)
VALUES (1, 'Harrison', 'Baxter'),
       (5, 'Harrison', 'Ratliff'),
       (3, 'Noble', 'Harrison');

-- remplir la table Debloque
INSERT INTO Debloque(num_succes, pseudo, date_deblocage)
VALUES (1, 'Harrison', '2022-01-25'),
       (2, 'Harrison', '2022-01-25'),
       (3, 'Harrison', '2022-01-26'),
       (4, 'Harrison', '2022-01-28'),
       (5, 'Joyce', '2022-01-28');
    