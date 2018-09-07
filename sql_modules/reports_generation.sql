/** The total sales for books in the previous month **/
/** All book sales **/
Select Sum(amount), sum(totalPrice) From Purchase where purchaseDate >= current_timestamp() - Interval 1 month;
/** Indivdual book sales **/
Select Purchase.isbn, title, Sum(amount), sum(totalPrice) From Purchase,Book where Book.isbn = Purchase.isbn and purchaseDate >= current_timestamp() - Interval 1 month group by Purchase.isbn;

-- -------------------------------------------------------------------------------------------------
/** The top 5 customers who purchase the most purchase amount in descending order for the last
three months**/
/** Top 5 users by money spent **/
Select username,firstName, lastName, email, phone, sum(totalPrice), sum(amount)from User,Purchase where User.uid = Purchase.uid and purchaseDate >= current_timestamp() - Interval 3 month group by Purchase.uid order by sum(totalPrice) desc limit 5;
/** Top 5 users by quantity purchased **/
Select username,firstName, lastName, email, phone, sum(totalPrice), sum(amount) from User, Purchase where User.uid = Purchase.uid and purchaseDate >= current_timestamp() - Interval 3 month group by Purchase.uid order by sum(amount) desc limit 5;
-- -------------------------------------------------------------------------------------------------
/** The top 10 selling books for the last three months **/
/** Top 10 books by money gain **/
Select Purchase.isbn, title, Sum(amount), sum(totalPrice) From Purchase, Book where Purchase.isbn = Book.isbn and purchaseDate >= current_timestamp() - Interval 3 month group by Purchase.isbn order by sum(totalPrice) desc limit 10;
/** Top 10 books by amount sold **/
Select Purchase.isbn, title, Sum(amount), sum(totalPrice) From Purchase, Book where Purchase.isbn = Book.isbn and purchaseDate >= current_timestamp() - Interval 3 month group by Purchase.isbn order by sum(amount) desc limit 10;