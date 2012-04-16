package edtscol.client.recherchereservation;

import java.util.ArrayList;

public interface ICtrlParam {

	public void setJourHeures(int jour, Integer heuredeb, Integer heurefin);

	public Integer getHeureDeb(int jour);

	/**
	 * L'heure de début si elle est unique pour la semaine, et non par jour...
	 * 
	 * @return
	 */
	public Integer getHeureDeb();

	public Integer getHeureFin(int jour);

	/**
	 * L'heure de fin si elle est unique pour la semaine, et non par jour...
	 * 
	 * @return
	 */
	public Integer getHeureFin();

	public Integer getDuree();

	/**
	 * La liste des jours cochés, format Calendar
	 * 
	 * @return
	 */
	public ArrayList<Integer> jours();

	/**
	 * TRUE si au moins les données de base pour pouvoir enregistrer sont présentes, c'est à dire créneau horaire (par jour) et durée
	 * 
	 * @return
	 */
	public boolean isUtilisable();

	/**
	 * TRUE si toutes les infos de paramétrage sont présentes, c'est à dire créneau horaire (par jour), durée, individu et salle
	 * 
	 * @return
	 */
	public boolean isComplet();

}
