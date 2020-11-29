
public enum MenuStateEnum
{
	START_MENU(new StartState()),
	WRITE_MENU(new WriteState()),
	READ_MENU(new ReadState()),
	TEST_SERVER_MENU(new TestServerState()),
	END_STATE(new EndState());
	
	private State state;

	MenuStateEnum(State s)
	{
		this.state = s;
	}
	
	public State getState()
	{
		return state;
	}
}
