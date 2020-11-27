/**
 * 
 * @author Dan Holmgren
 *
 * This class provides the menu for testing the servers
 */
public class TestServerState extends State {

  /**
   * 
   */
  TestServerState(){
    MenuOption[] myMenuOptions = 
      { new MenuOptionForMenu("Go Back", MenuStateEnum.START_MENU),
          new MenuOptionForAction("Test the servers", new TestServersAction()),
          new MenuOptionForMenu("Exit", MenuStateEnum.END_STATE)};
      }
}
