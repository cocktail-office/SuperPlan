package edtscol.client.gestioncontraintes;

import org.cocktail.superplan.client.metier.ContrainteSemaine;
import org.cocktail.superplan.client.metier.FormationAnnee;

import com.webobjects.foundation.NSArray;

public interface IRessourceContrainte {

	/**
	 * Doit retourner un tableau des ContrainteSemaine trié par date ascendante
	 * 
	 * @param formationAnnee
	 * @return
	 */
	public NSArray<ContrainteSemaine> getContrainteSemaines(FormationAnnee formationAnnee);

}
