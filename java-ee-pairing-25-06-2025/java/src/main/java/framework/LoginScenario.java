package framework;

public class LoginScenario implements IScenario{
    @Override
    public void init() {

    }

    @Override
    public void execute() {
        UserDetails userDetails = new UserDetails();
        userDetails.execute();
    }
}
