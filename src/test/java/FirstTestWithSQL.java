import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FirstTestWithSQL {

    @BeforeEach
    void setUp() throws SQLException {

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ){}
    }

    @AfterEach
    void DelInfo() throws SQLException {

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
       ) {
            val finishRunner = new QueryRunner();

            val delCode = "delete from auth_codes";
            val delCards = "delete from cards";
            val delUser = "delete from users";

            val deleteCode = finishRunner.update(conn, delCode);
            val deleteCards = finishRunner.update(conn, delCards);
            val deleteUser = finishRunner.update(conn, delUser);
            System.out.println(deleteCode);
            System.out.println(deleteCards);
            System.out.println(deleteUser);

       }
   }

    @Test
    void stubTest() throws SQLException {

        val runner = new QueryRunner();

        val loginSQL = "SELECT login FROM users where id ='65653d07-5845-40f6-9eaf-6ed754a68455'";
        val codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC  limit 1";


        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {

            String loginUser = runner.query(conn, loginSQL, new ScalarHandler<>());
            System.out.println(loginUser);
            String codeUser = runner.query(conn, codeSQL, new ScalarHandler<>());

            open("http://localhost:9999");
            $("[data-test-id=login] input").setValue(loginUser);
            $("[data-test-id=password] input").setValue("qwerty123");
            $(".button").click();
            $("[data-test-id=code] input").setValue(codeUser);

        }

    }
    }
