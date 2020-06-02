package CommandInterface;

import java.util.Stack;

public class ShowCommand implements InterfaceCommand {

	private Stack<String> HistoriqueCommand;

	public ShowCommand(Stack<String> newHistoriqueCommand) {
		setHistoriqueCommand(newHistoriqueCommand);
	}

	@Override
	public void execute() {
		if(!getHistoriqueCommand().isEmpty()) {
			for(String command : getHistoriqueCommand()) {
				System.out.println(command);
			}
		}
	}

	public Stack<String> getHistoriqueCommand() {
		return HistoriqueCommand;
	}

	public void setHistoriqueCommand(Stack<String> historiqueCommand) {
		HistoriqueCommand = historiqueCommand;
	}

}
