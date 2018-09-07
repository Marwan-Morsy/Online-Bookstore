Use bookstore;

drop procedure if exists populate;

Delimiter $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `populate`()
BEGIN
	DECLARE x MEDIUMTEXT default 0;

	SET x = 2;  -- to use this multiple times you can just

						-- change the starting number and run again
						-- if you do not want duplicate numbers
	Start transaction;
		WHILE x < 500000  -- any value you want
		 do
			insert into Book(ISBN,Title,PublicationDate,Threshold,Price,CopiesNumber,CID,PID)
				values(x, concat('hello world ',x), curdate() - interval x day, 10 , 20 + rand()*(200), 10 +floor(rand()*(50)), 1 + round(rand()), 1);
            set x = x + 1;  -- increment counter
		end While;
    commit;
END $$

Delimiter ;

call populate();
drop procedure populate;