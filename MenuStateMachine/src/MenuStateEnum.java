
public enum MenuStateEnum
{
	START_MENU(new StartState()),
	SECOND_MENU(new WriteState()),
	ANOTHER_MENU(new ReadState()),
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
