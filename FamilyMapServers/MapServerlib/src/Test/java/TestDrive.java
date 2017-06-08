/**
 * Created by brandonderbidge on 5/31/17.
 */


public class TestDrive {

    public static void main(String[] args) {
//        org.junit.runner.JUnitCore.runClasses(
//                DatabaseDAOTest.class,
//                EventDAOTest.class,
//                PersonDAOTest.class,
//                UserDAO.class,
//                EventTest.class,
//                PersonTest.class,
//                UserTest.class,
//                ClearServiceTest.class,
//                EventServiceTest.class,
//                LoginServiceTest.class,
//                RegisterServiceTest.class,
//                TestFillService.class
//                );

        org.junit.runner.JUnitCore.main(
                "DataAccess.DatabaseDAOTest",
                "DataAccess.EventDAOTest",
                "DataAccess.PersonDAOTest",
                "DataAccess.UserDAOTest",
                "Models.EventTest",
                "Models.PersonTest",
                "Models.UserTest",
                "Services.ClearServiceTest",
                "Services.EventServiceTest",
                "Services.LoginServiceTest",
                "Services.RegisterServiceTest",
                "Services.TestFillService");
    }
}