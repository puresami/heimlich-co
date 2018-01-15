package games.HeimlichUndCo;

import java.util.Comparator;

public class MarkerComparator  implements Comparator<Agent> {
/*
 * (non-Javadoc)
 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
 */
	@Override
	public int compare(Agent o1, Agent o2) {
		return o1.getMarkerPosition() - o2.getMarkerPosition();
	}

}
