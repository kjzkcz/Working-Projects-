CREATE TABLE `User` 
(
	    username varchar(255) PRIMARY KEY,
    	`password` varchar(128) NOT NULL,
    	fName varchar(80) DEFAULT "",
    	lName varchar(80) DEFAULT "",
    	bio varchar(1000) DEFAULT ""
);

CREATE TABLE Shop (
    `Name` varchar(255) NOT NULL,
    `Address` varchar(255) DEFAULT "",
    Shop_Id int AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE `Owns` 
(
	username varchar(255),
    Shop_Id int,
    PRIMARY KEY (username, Shop_Id),
    FOREIGN KEY (username) REFERENCES `User` (username),
    FOREIGN KEY (Shop_Id) REFERENCES Shop(Shop_Id)
);

CREATE TABLE Visits
(
	username varchar(255),
    Shop_Id int,
    PRIMARY KEY (username, Shop_Id),
    FOREIGN KEY (username) REFERENCES `User` (username),
    FOREIGN KEY (Shop_Id) REFERENCES Shop(Shop_Id)
);

CREATE TABLE `Hours`
(
    Shop_Id int,
    `day` ENUM ('Monday','Tuesday','Wednesday','Thurday','Friday','Saturday','Sunday') NOT NULL,
    `start_time` time NOT NULL,
    `end_time` time NOT NULL,
    PRIMARY KEY (Shop_Id, `day`),
    FOREIGN KEY (Shop_Id) REFERENCES Shop(Shop_Id)
);

CREATE TABLE Item
(
    Item_Id int AUTO_INCREMENT PRIMARY KEY,
    Calories int,
    Temperature DECIMAL(10,2),
    `Name` varchar(200) NOT NULL,
    Flavor varchar(100),
    `Description` varchar(200),
    Item_Type enum ("Beverage", "Food") NOT NULL
);

CREATE TABLE Preference
(
	username varchar(255),
    Item_Id int,
    likert int NOT NULL CHECK (likert IN (1,2,3,4,5)),
    PRIMARY KEY (username, Item_Id),
    FOREIGN KEY (username) REFERENCES `User` (username),
    FOREIGN KEY (Item_Id) REFERENCES Item (Item_Id)
);

CREATE TABLE Prices 
(
	Price_Id int AUTO_INCREMENT PRIMARY KEY,
    Price DECIMAL(10,2) NOT NULL,    		
    SizeName varchar(80) NOT NULL,
    SizeUnit varchar(80),
    SizeAmount DECIMAL(10,2)
    CHECK (Price >= 0)
);

CREATE TABLE Offers 
(
	Shop_Id int,
    Item_Id int,
    Price_Id int,
    PRIMARY KEY (Shop_Id, Item_Id, Price_Id),
    FOREIGN KEY (Shop_Id) REFERENCES Shop(Shop_Id),
    FOREIGN KEY (Item_Id) REFERENCES Item(Item_Id),
    FOREIGN KEY (Price_Id) REFERENCES Prices(Price_Id)
);

CREATE TABLE Item2
(
	Item_Id int,
    Category varchar(80) NOT NULL,
    PRIMARY KEY (Item_Id, Category),
    FOREIGN KEY (Item_Id) REFERENCES Item(Item_Id)   
);

CREATE TABLE Beverage
(
   Item_Id int PRIMARY KEY,
   is_caffeinated boolean NOT NULL,
   FOREIGN KEY (Item_Id) REFERENCES Item(Item_Id)
);

CREATE TABLE Beverage2
(
    Item_Id int,
    `Name` varchar(255) NOT NULL,
    Quantity int NOT NULL,
    PRIMARY KEY (Item_Id, `Name`, Quantity),
    FOREIGN KEY (Item_Id) REFERENCES Beverage(Item_Id)
);

CREATE TABLE Food
(
    Item_Id int,
    meal_of_day ENUM ('Breakfast', 'Lunch', 'Dinner') NULL,
    PRIMARY KEY (Item_Id),
    FOREIGN KEY (Item_Id) REFERENCES Item(Item_Id)
);

CREATE TABLE Purchased
(
    Shop_Id int,
    username varchar(255),
    Item_Id int,
    `timestamp` datetime NOT NULL,
    comment varchar(1000),
    rating int CHECK (rating IN (1, 2, 3, 4, 5)),
    PRIMARY KEY (username, Shop_Id, Item_Id),
    FOREIGN KEY (Shop_Id) REFERENCES Shop(Shop_Id),
    FOREIGN KEY (username) REFERENCES User(username),
    FOREIGN KEY (Item_Id) REFERENCES Item(Item_Id)
);

INSERT INTO `Item` (`Item_Id`, `Calories`, `Temperature`, `Name`, `Flavor`, `Description`, `Item_Type`) VALUES
(1, 5, '160.00', 'Caffe Americano', 'Americano', 'Short Espresso shots topped with hot water.', 'Beverage'),
(2, 5, '160.00', 'Blonde Roast', 'Brewed Coffee', 'Lightly roasted coffee that\'s soft, mellow and flavorful.', 'Beverage'),
(3, 30, '160.00', 'Cafe Misto', 'Brewed Coffee', 'A one-to-one combination of fresh-brewed coffee coffee and steamed milk.', 'Beverage'),
(4, 5, '160.00', 'Signature Roast', 'Brewed Coffee', NULL, 'Beverage'),
(5, 4, '170.00', 'Signature Decaf', 'Brewed Coffee', 'A Decaf variety of our signature brew', 'Beverage'),
(6, 30, '170.00', 'Cappuccino', 'Cappuccino', 'Dark, rich espresso lies in wait under a smoothed and stretched layer of thick milk foam', 'Beverage'),
(7, 5, '175.00', 'Espresso', 'Espresso Shot', 'Smooth signature espresso roast', 'Beverage'),
(8, 25, '120.00', 'Espresso con Panna', 'Espresso Shot', 'Espresso meets a dollop of whipped cream', 'Beverage'),
(9, 50, '160.00', 'Cafe Latte', NULL, NULL, 'Beverage'),
(10, 45, NULL, 'Pumpkin Spice Latte', NULL, NULL, 'Beverage'),
(11, 60, NULL, 'Caramel Macchiato', 'Macchiato', NULL, 'Beverage'),
(12, 15, NULL, 'Espresso Macchiato', 'Macchiato', NULL, 'Beverage'),
(13, 80, NULL, 'Signature Mocha', 'Mocha', 'Rich, full-bodied espresso combined with bittersweet mocha sauce', 'Beverage'),
(14, 100, NULL, 'Peppermint Mocha', 'Mocha', NULL, 'Beverage'),
(15, 0, '180.00', 'Chai Tea', 'Chai', NULL, 'Beverage'),
(16, 60, '180.00', 'Chai Latte', 'Chai', 'Black tea infused with cinnamon,clove and other warming spices is combined with steamed milk and topped with foam.', 'Beverage'),
(17, 0, '180.00', 'Earl Grey Tea', 'Black Tea', NULL, 'Beverage'),
(18, 40, '180.00', 'London Fog Tea Latte', 'Black Tea', NULL, 'Beverage'),
(19, 0, '180.00', 'Royal English Breakfast Tea', 'Black Tea', NULL, 'Beverage'),
(20, 40, '180.00', 'Royal English Breakfast Tea Latte', 'Black Tea', NULL, 'Beverage'),
(21, 80, '180.00', 'Matcha Green Tea Latte', 'Green Tea', NULL, 'Beverage'),
(22, 0, '180.00', 'Emperor\'s Clouds & Mist', 'Green Tea', NULL, 'Beverage'),
(23, 0, '180.00', 'Mint Majesty', 'Herbal Tea', NULL, 'Beverage'),
(24, 0, '180.00', 'Peach Tranquility', 'Herbal Tea', NULL, 'Beverage'),
(25, 30, '90.00', 'Iced Tea', 'Green Tea', NULL, 'Beverage'),
(26, 80, '100.00', 'Sweet Tea', 'Green Tea', NULL, 'Beverage'),
(27, 0, '180.00', 'Chai Tea', 'Chai', NULL, 'Beverage'),
(28, 540, NULL, 'Crispy Grilled Cheese Sandwich', NULL, 'A delicious blend of white Cheddar and mozzarella cheeses on sourdough bread topped with a Parmasan butter spread.', 'Food'),
(29, 280, NULL, 'Plain Bagel', NULL, NULL, 'Food'),
(30, 270, NULL, 'Blueberry Bagel', 'Blueberry', NULL, 'Food'),
(31, 290, NULL, 'Everything Bagel', 'Everything Spice', NULL, 'Food'),
(32, 260, NULL, 'Croissant', NULL, 'Fluffy wonder of the world', 'Food'),
(33, 380, '140.00', 'Blueberry Scone', 'Blueberry', NULL, 'Food');

INSERT INTO `Item2` (`Item_Id`, `Category`) VALUES
(1, 'Hot Coffees'),
(2, 'Hot Coffees'),
(3, 'Hot Coffees'),
(4, 'Hot Coffees'),
(5, 'Hot Coffees'),
(6, 'Hot Coffees'),
(7, 'Hot Coffees'),
(8, 'Hot Coffees'),
(9, 'Hot Coffees'),
(10, 'Fall Seasonal'),
(11, 'Hot Coffees'),
(12, 'Hot Coffees'),
(13, 'Hot Coffees'),
(14, 'Fall Seasonal'),
(15, 'Hot Teas'),
(16, 'Hot Teas'),
(17, 'Hot Teas'),
(18, 'Hot Teas'),
(19, 'Hot Teas'),
(20, 'Hot Teas'),
(21, 'Hot Teas'),
(22, 'Hot Teas'),
(23, 'Hot Teas'),
(24, 'Hot Teas'),
(25, 'Iced Teas'),
(32, 'Desert'),
(33, 'Desert');

INSERT INTO `Beverage` (`Item_Id`, `is_caffeinated`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 0),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 1),
(19, 1),
(20, 1),
(21, 1),
(22, 1),
(23, 0),
(24, 0),
(25, 0),
(26, 0),
(27, 1);

INSERT INTO `Beverage2` (`Item_Id`, `Name`, `Quantity`) VALUES
(1, 'Shot of espresso', 2),
(1, 'Shot of espresso', 4),
(3, 'Shot of steamed milk', 1),
(6, 'Shot of espresso', 2),
(6, 'Shot of espresso', 4),
(6, 'Shot of milk foam', 1),
(7, 'Shot of espresso', 2),
(7, 'Shot of espresso', 4),
(8, 'Shot of espresso', 2),
(8, 'Shot of espresso', 4),
(8, 'Shot of whipped cream', 1),
(9, 'Shot of espresso', 2),
(9, 'Shot of espresso', 4),
(9, 'Shot of milk foam', 1),
(9, 'Shot of steamed milk', 1),
(10, 'Shot of espresso', 2),
(10, 'Shot of espresso', 4),
(10, 'Shot of pumpkin spice blend', 1),
(10, 'Shot of steamed milk', 1),
(11, 'Shot of espresso', 2),
(11, 'Shot of espresso', 4),
(11, 'Shot of steamed milk', 1),
(11, 'Shot of vanilla', 1),
(11, 'Topped with caramel drizzle', 1),
(12, 'Shot of espresso', 2),
(12, 'Shot of espresso', 4),
(12, 'Shot of foam', 1),
(12, 'Shot of steamed milk', 1);

INSERT INTO `Food` (`Item_Id`, `meal_of_day`) VALUES
(28, 'Lunch'),
(29, 'Breakfast'),
(30, 'Breakfast'),
(31, 'Breakfast');

INSERT INTO `Shop` (`Name`, `Address`, `Shop_Id`) VALUES
('Starbucks', NULL, 1),
('Lakota', NULL, 2),
('Caribou', NULL, 3),
('Kaldi\'s', '700 De Mun Ave, St. Louis, MO 63105', 4);

INSERT INTO `Hours` (`Shop_Id`, `day`, `start_time`, `end_time`) VALUES
(1, 'Monday', '13:00:00', '19:00:00'),
(1, 'Wednesday', '13:00:00', '19:00:00'),
(1, 'Friday', '13:00:00', '19:00:00'),
(2, 'Monday', '13:00:00', '19:00:00'),
(2, 'Wednesday', '13:00:00', '19:00:00'),
(2, 'Friday', '13:00:00', '19:00:00'),
(3, 'Monday', '13:00:00', '19:00:00'),
(3, 'Wednesday', '13:00:00', '19:00:00'),
(3, 'Friday', '13:00:00', '19:00:00');

INSERT INTO `User` (`username`, `password`, `fName`, `lName`, `bio`) VALUES
('esparzaderek', 'OBJ4EtJkFrfe', 'Lee', 'Abbott', 'Admit of around piece government. Physical agency and difficult president at artist.\r\nThroughout describe itself group. Power road size impact. Important enough most.'),
('holly23', '7yPYxyp80wBz', 'Troy', 'Collins', 'Best budget power them evidence without beyond take. Physical against trial son break either. Stage population boy child surface amount day.'),
('ksmith', 'NW25nWLqQ7Ht', 'Lauren', 'Melton', 'Surface something prevent a consider. Now four management energy stay significant his. Artist political camera expert stop area. Loss cell three.'),
('lucas07', 'eg929JCnLyQK', 'Steven', 'Rivera', 'Report town almost plan that hair sea quality. Particularly partner relate mention expect.\r\nLearn place might what western upon. Ok majority region democratic entire analysis. Glass face according as.'),
('matthew90', 'DU2pg9AYnz55', 'Thomas', 'Randall', 'Better news face. Small research describe base detail yourself one. Issue grow ask tell.\r\nLater quality budget huge debate among way.'),
('millercharles', '7QFSw8SfTM0h', 'Angela', 'Watts', 'Boy without feeling participant interest. Begin marriage which myself if place again.\r\nA huge three. Know second government the pull cultural.\r\nAudience energy move. Morning eat turn clear.'),
('msanchez', '0Tj3gXcfG4IR', 'Tina', 'Johnson', 'Beyond write current grow rule stuff truth college.\r\nSmall citizen class morning. Others kind company likely.'),
('ostone', '1lj1Rc1uxi4N', 'Robert', 'Miller', 'Concern store discover hand others century. Daughter purpose voice. Security fall ready usually.\r\nCost both general where. Agreement decade friend which.'),
('paul75', 'FDqHLz6f6Bim', 'Angelica', 'Tanner', 'Evening admit past Republican common. Eat expect save process score middle. Business population brother.');


INSERT INTO `Owns` (`username`, `Shop_Id`) VALUES
('esparzaderek', 1),
('holly23', 1),
('ksmith', 2),
('matthew90', 4),
('millercharles', 3);

INSERT INTO `Preference` (`username`, `Item_Id`, `likert`) VALUES
('esparzaderek', 1, 4),
('esparzaderek', 8, 3),
('esparzaderek', 33, 5),
('holly23', 2, 3),
('holly23', 9, 3),
('holly23', 13, 5),
('holly23', 19, 4),
('holly23', 27, 1),
('ksmith', 6, 2),
('ksmith', 7, 5),
('ksmith', 20, 3),
('lucas07', 20, 5),
('lucas07', 30, 3),
('matthew90', 4, 1),
('matthew90', 21, 5),
('millercharles', 12, 4),
('msanchez', 12, 1),
('msanchez', 14, 1),
('msanchez', 19, 1),
('msanchez', 23, 1),
('msanchez', 25, 1),
('ostone', 2, 4),
('ostone', 6, 3),
('ostone', 22, 5),
('ostone', 26, 4),
('ostone', 28, 5),
('paul75', 12, 4),
('paul75', 13, 2),
('paul75', 15, 2),
('paul75', 18, 3);

INSERT INTO `Prices` (`Price_Id`, `Price`, `SizeName`, `SizeUnit`, `SizeAmount`) VALUES
(1, '3.50', 'Small', 'fl oz', '8.00'),
(2, '4.50', 'Medium', 'fl oz', '12.00'),
(3, '5.50', 'Large', 'fl oz', '16.00'),
(4, '6.50', 'Venti', 'fl oz', '20.00'),
(5, '8.00', 'Whole', NULL, NULL),
(6, '8.00', 'Half', NULL, NULL),
(7, '3.75', '', NULL, NULL);

INSERT INTO `Purchased` (`Shop_Id`, `username`, `Item_Id`, `timestamp`, `comment`, `rating`) VALUES
(1, 'esparzaderek', 4, '2020-12-06 19:33:57', 'Lorem ipsum dolor sit amet...', 4),
(2, 'ksmith', 23, '2020-12-06 19:33:57', 'Lorem ipsum dolor sit amet...', 3),
(3, 'millercharles', 1, '2020-12-06 19:33:57', 'Lorem ipsum dolor sit amet...', 5),
(4, 'paul75', 9, '2020-12-06 19:33:57', 'Lorem ipsum dolor sit amet...', 2);

INSERT INTO `Offers` (`Shop_Id`, `Item_Id`, `Price_Id`) VALUES
(1, 1, 1),
(1, 1, 2),
(1, 1, 3),
(1, 1, 4),
(1, 2, 1),
(1, 2, 2),
(1, 2, 3),
(1, 2, 4),
(1, 3, 1),
(1, 3, 2),
(1, 3, 3),
(1, 3, 4),
(1, 4, 1),
(1, 4, 2),
(1, 4, 3),
(1, 4, 4),
(1, 5, 1),
(1, 5, 2),
(1, 5, 3),
(1, 5, 4),
(1, 6, 1),
(1, 6, 2),
(1, 6, 3),
(1, 6, 4),
(1, 7, 1),
(1, 7, 2),
(1, 7, 3),
(1, 7, 4),
(1, 8, 1),
(1, 8, 2),
(1, 8, 3),
(1, 8, 4),
(1, 9, 1),
(1, 9, 2),
(1, 9, 3),
(1, 9, 4),
(1, 10, 1),
(1, 10, 2),
(1, 10, 3),
(1, 10, 4),
(1, 11, 1),
(1, 11, 2),
(1, 11, 3),
(1, 11, 4),
(1, 12, 1),
(1, 12, 2),
(1, 12, 3),
(1, 12, 4),
(1, 13, 1),
(1, 13, 2),
(1, 13, 3),
(1, 13, 4),
(1, 14, 1),
(1, 14, 2),
(1, 14, 3),
(1, 14, 4),
(1, 15, 1),
(1, 15, 2),
(1, 15, 3),
(1, 15, 4),
(1, 16, 1),
(1, 16, 2),
(1, 16, 3),
(1, 16, 4),
(1, 17, 1),
(1, 17, 2),
(1, 17, 3),
(1, 17, 4),
(1, 18, 1),
(1, 18, 2),
(1, 18, 3),
(1, 18, 4),
(1, 19, 1),
(1, 19, 2),
(1, 19, 3),
(1, 19, 4),
(1, 20, 1),
(1, 20, 2),
(1, 20, 3),
(1, 20, 4),
(1, 21, 1),
(1, 21, 2),
(1, 21, 3),
(1, 21, 4),
(1, 22, 1),
(1, 22, 2),
(1, 22, 3),
(1, 22, 4),
(1, 23, 1),
(1, 23, 2),
(1, 23, 3),
(1, 23, 4),
(1, 24, 1),
(1, 24, 2),
(1, 24, 3),
(1, 24, 4),
(1, 25, 1),
(1, 25, 2),
(1, 25, 3),
(1, 25, 4),
(1, 26, 1),
(1, 26, 2),
(1, 26, 3),
(1, 26, 4),
(1, 27, 1),
(1, 27, 2),
(1, 27, 3),
(1, 27, 4),
(1, 28, 5),
(1, 28, 6),
(1, 29, 7),
(1, 30, 7),
(1, 31, 7),
(1, 32, 7),
(1, 33, 7),
(2, 1, 1),
(2, 1, 2),
(2, 1, 3),
(2, 2, 1),
(2, 2, 2),
(2, 2, 3),
(2, 3, 1),
(2, 3, 2),
(2, 3, 3),
(2, 4, 1),
(2, 4, 2),
(2, 4, 3),
(2, 5, 1),
(2, 5, 2),
(2, 5, 3),
(2, 6, 1),
(2, 6, 2),
(2, 6, 3),
(2, 7, 1),
(2, 7, 2),
(2, 7, 3),
(2, 8, 1),
(2, 8, 2),
(2, 8, 3),
(2, 9, 1),
(2, 9, 2),
(2, 9, 3),
(2, 10, 1),
(2, 10, 2),
(2, 10, 3),
(2, 11, 1),
(2, 11, 2),
(2, 11, 3),
(2, 12, 1),
(2, 12, 2),
(2, 12, 3),
(2, 13, 1),
(2, 13, 2),
(2, 13, 3),
(2, 14, 1),
(2, 14, 2),
(2, 14, 3),
(2, 15, 1),
(2, 15, 2),
(2, 15, 3),
(2, 16, 1),
(2, 16, 2),
(2, 16, 3),
(2, 17, 1),
(2, 17, 2),
(2, 17, 3),
(2, 18, 1),
(2, 18, 2),
(2, 18, 3),
(2, 19, 1),
(2, 19, 2),
(2, 19, 3),
(2, 20, 1),
(2, 20, 2),
(2, 20, 3),
(2, 21, 1),
(2, 21, 2),
(2, 21, 3),
(2, 22, 1),
(2, 22, 2),
(2, 22, 3),
(2, 23, 1),
(2, 23, 2),
(2, 23, 3),
(2, 24, 1),
(2, 24, 2),
(2, 24, 3),
(2, 25, 1),
(2, 25, 2),
(2, 25, 3),
(2, 26, 1),
(2, 26, 2),
(2, 26, 3),
(2, 27, 1),
(2, 27, 2),
(2, 27, 3),
(2, 28, 5),
(2, 28, 6),
(2, 29, 7),
(2, 30, 7),
(2, 31, 7),
(2, 32, 7),
(2, 33, 7),
(3, 1, 1),
(3, 1, 2),
(3, 1, 3),
(3, 2, 1),
(3, 2, 2),
(3, 2, 3),
(3, 3, 1),
(3, 3, 2),
(3, 3, 3),
(3, 4, 1),
(3, 4, 2),
(3, 4, 3),
(3, 5, 1),
(3, 5, 2),
(3, 5, 3),
(3, 6, 1),
(3, 6, 2),
(3, 6, 3),
(3, 7, 1),
(3, 7, 2),
(3, 7, 3),
(3, 8, 1),
(3, 8, 2),
(3, 8, 3),
(3, 9, 1),
(3, 9, 2),
(3, 9, 3),
(3, 10, 1),
(3, 10, 2),
(3, 10, 3),
(3, 11, 1),
(3, 11, 2),
(3, 11, 3),
(3, 12, 1),
(3, 12, 2),
(3, 12, 3),
(3, 13, 1),
(3, 13, 2),
(3, 13, 3),
(3, 14, 1),
(3, 14, 2),
(3, 14, 3),
(3, 15, 1),
(3, 15, 2),
(3, 15, 3),
(3, 16, 1),
(3, 16, 2),
(3, 16, 3),
(3, 17, 1),
(3, 17, 2),
(3, 17, 3),
(3, 18, 1),
(3, 18, 2),
(3, 18, 3),
(3, 19, 1),
(3, 19, 2),
(3, 19, 3),
(3, 20, 1),
(3, 20, 2),
(3, 20, 3),
(3, 21, 1),
(3, 21, 2),
(3, 21, 3),
(3, 22, 1),
(3, 22, 2),
(3, 22, 3),
(3, 23, 1),
(3, 23, 2),
(3, 23, 3),
(3, 24, 1),
(3, 24, 2),
(3, 24, 3),
(3, 25, 1),
(3, 25, 2),
(3, 25, 3),
(3, 26, 1),
(3, 26, 2),
(3, 26, 3),
(3, 27, 1),
(3, 27, 2),
(3, 27, 3),
(3, 28, 5),
(3, 28, 6),
(3, 29, 7),
(3, 30, 7),
(3, 31, 7),
(3, 32, 7),
(3, 33, 7),
(4, 1, 1),
(4, 1, 3),
(4, 2, 1),
(4, 2, 3),
(4, 3, 1),
(4, 3, 3),
(4, 4, 1),
(4, 4, 3),
(4, 5, 1),
(4, 5, 3),
(4, 6, 1),
(4, 6, 3),
(4, 7, 1),
(4, 7, 3),
(4, 8, 1),
(4, 8, 3),
(4, 9, 1),
(4, 9, 3),
(4, 10, 1),
(4, 10, 3),
(4, 11, 1),
(4, 11, 3),
(4, 12, 1),
(4, 12, 3),
(4, 13, 1),
(4, 13, 3),
(4, 14, 1),
(4, 14, 3),
(4, 15, 1),
(4, 15, 3),
(4, 16, 1),
(4, 16, 3),
(4, 17, 1),
(4, 17, 3),
(4, 18, 1),
(4, 18, 3),
(4, 19, 1),
(4, 19, 3),
(4, 20, 1),
(4, 20, 3),
(4, 21, 1),
(4, 21, 3),
(4, 22, 1),
(4, 22, 3),
(4, 23, 1),
(4, 23, 3),
(4, 24, 1),
(4, 24, 3),
(4, 25, 1),
(4, 25, 3),
(4, 26, 1),
(4, 26, 3),
(4, 27, 1),
(4, 27, 3),
(4, 33, 7);



INSERT INTO `Visits` (`username`, `Shop_Id`) VALUES
('esparzaderek', 1),
('holly23', 1),
('ksmith', 2),
('lucas07', 1),
('lucas07', 4),
('matthew90', 4),
('millercharles', 3),
('msanchez', 3),
('ostone', 1),
('ostone', 2),
('paul75', 2),
('paul75', 4);

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addUser`(IN `username` VARCHAR(255), IN `password` VARCHAR(128), IN `fname` VARCHAR(80), IN `lname` VARCHAR(80), IN `bio` VARCHAR(1000))
    NO SQL
INSERT INTO `User`
(username, password, fname, lname, bio)
VALUES
(@p0,@p1,@p2,@p3,@p4)$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addPreference`(IN `username` VARCHAR(255), IN `item_id` INT(1), IN `likert` INT(1))
    NO SQL
BEGIN
INSERT INTO Preference
(username, item_id, likert)
VALUES 
(@p0, @p1, @p2);
SELECT * FROM Preference;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteUser`(IN `username` VARCHAR(255) CHARSET utf8)
    NO SQL
DELETE FROM `User`
WHERE `User.username` = @p0 COLLATE utf8mb4_general_ci$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `changePreference`(IN `likert` INT(1) UNSIGNED, IN `username` VARCHAR(255) CHARSET utf8mb4, IN `item_id` INT(1))
    MODIFIES SQL DATA
UPDATE Preference
SET likert = @p0
WHERE username = @p1 COLLATE utf8mb4_general_ci
AND Item_Id= @p2 COLLATE utf8mb4_general_ci$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `joinUsernamePreference`()
    NO SQL
SELECT `User`.fname as FirstName, `User`.`lName` as LastName, Preference.Item_Id, Item.Name, Preference.likert 
FROM `User`,Preference, Item
WHERE
`User`.username=Preference.username
AND Item.Item_Id = Preference.Item_Id$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `leaveComment/rating`(IN `Shop ID` INT(1), IN `Username` VARCHAR(255), IN `Item ID` INT(1), IN `comment/review` VARCHAR(1000), IN `rating` INT(1))
    NO SQL
INSERT INTO Purchased (Shop_Id, username, Item_Id, timestamp, comment, rating) VALUES(@p0, @p1, @p2 , NOW(), @p3, @p4)$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pullupMenu`(IN `shopId` INT)
    NO SQL
BEGIN
SELECT Shop.Name as ShopName, Item.Name as ItemName, Item.Description, Item.Item_Type, Prices.Price, Prices.SizeName, Prices.SizeAmount, Prices.SizeUnit FROM `Offers`, `Shop`, `Item`,`Prices`, `Beverage` WHERE Offers.Shop_Id = Shop.Shop_Id AND Offers.Item_Id = Item.Item_Id AND Offers.Price_Id = Prices.Price_Id AND Beverage.Item_Id = Item.Item_Id AND Shop.Shop_Id = @p0;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `recommendItem`(IN `username` VARCHAR(255))
    NO SQL
BEGIN

    CREATE TEMPORARY TABLE ItemCategories SELECT Item2.Category FROM Item, Item2, Preference, `User` WHERE `User`.`username` = Preference.username AND Preference.Item_Id = Item.Item_Id AND Item.Item_Id = Item2.Item_Id
    AND `User`.username = @p0 COLLATE utf8mb4_general_ci;

    CREATE TEMPORARY TABLE RecommendedItems SELECT * FROM Item 
    WHERE EXISTS
    (SELECT Item.Item_Id FROM Item2, ItemCategories WHERE Item.Item_Id = Item2.Item_Id AND Item2.Category = ItemCategories.Category);

	
    SELECT AVG(Preference.likert) AS Likert, Item2.Category, RecommendedItems.* FROM RecommendedItems, Preference, Item2 WHERE Preference.Item_Id = RecommendedItems.Item_Id AND Item2.Item_Id = RecommendedItems.Item_Id GROUP BY RecommendedItems.Item_Id ORDER BY Item2.Category, Likert DESC;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showComments/Purchases`()
    NO SQL
SELECT * FROM Purchased$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showDrinks`()
    NO SQL
SELECT Item.Item_Id, Item.Name, Beverage.is_caffeinated, Item.Item_Type FROM Item, Beverage WHERE Item.Item_Id = Beverage.Item_Id$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showItems`()
    NO SQL
SELECT * FROM Item$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showFood`()
    NO SQL
SELECT Item.Item_Id, Item.Name, Food.meal_of_day, Item.Item_Type FROM Item, Food WHERE Item.Item_Id = Food.Item_Id$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showPreference`()
    NO SQL
SELECT * FROM Preference$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showShopHours`()
    NO SQL
SELECT DISTINCT Shop.name, Hours.day, Hours.start_time, Hours.end_time
FROM Hours, Shop WHERE Hours.Shop_Id=Shop.Shop_Id$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showShops`()
    NO SQL
SELECT * FROM Shop$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `showUser`()
    NO SQL
SELECT * FROM `User`$$
DELIMITER ;
