package CommandInterface;

import java.util.Stack;

/**
 * Command pour afficher l'historique des requetes.
 * @author root
 *
 */
public class ShowCommand implements InterfaceCommand {
	
	
	private Stack<String> HistoriqueCommand;
	
	/**
	 * Constructeur de la classe
	 * @param newHistoriqueCommand pile qui contient l'historique.
	 */
	public ShowCommand(Stack<String> newHistoriqueCommand) {
		setHistoriqueCommand(newHistoriqueCommand);
	}

	/**
	 * Affiche l'historique.
	 */
	@Override
	public void execute() {
		if(!getHistoriqueCommand().isEmpty()) {
			for(String command : getHistoriqueCommand()) {
				System.out.println(command);
			}
		}
	}
	/**
	 * retourne la pile contenant l'historique des command.
	 * @return HistoriqueCommand pile contenant les requetes t'apées par l'utilisateur.
	 */
	public Stack<String> getHistoriqueCommand() {
		return HistoriqueCommand;
	}

	public void setHistoriqueCommand(Stack<String> historiqueCommand) {
		HistoriqueCommand = historiqueCommand;
	}

}
