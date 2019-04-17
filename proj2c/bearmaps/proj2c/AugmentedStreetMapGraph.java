package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.lab9.MyTrieSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private List<Point> spots;
    private Map<Point, Node> point2node;
    private Map<String, List<String>> cleanName2fullNames;
    private Map<String, List<Node>> cleanName2Nodes;
    private MyTrieSet cleanNames;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        spots = new ArrayList<>();
        point2node = new HashMap<>();
        cleanNames = new MyTrieSet();
        cleanName2fullNames = new HashMap<>();
        cleanName2Nodes = new HashMap<>();
        for(Node n : nodes) {
            Point p = new Point(n.lon(), n.lat());
            point2node.put(p, n);
            if(!neighbors(n.id()).isEmpty()) {
                spots.add(p);
            }
            if(n.name() != null) {
                String CN = n.name().toLowerCase().replaceAll("\\s+","");
                if (!cleanName2fullNames.containsKey(CN)) {
                    cleanNames.add(CN);
                    List<String> s = new LinkedList<>();
                    cleanName2fullNames.put(CN, s);
                    List<Node> s1 = new LinkedList<>();
                    cleanName2Nodes.put(CN, s1);
                }
                cleanName2fullNames.get(CN).add(n.name());
                cleanName2Nodes.get(CN).add(n);
            }
        }

    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        WeirdPointSet w = new WeirdPointSet(spots);
        Point p = w.nearest(lon, lat);
        return point2node.get(p).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> l = new LinkedList<>();
        List<String> CNs = cleanNames.keysWithPrefix(prefix.toLowerCase().replaceAll("\\s+",""));
        for(String CN : CNs) {
            for(String FN : cleanName2fullNames.get(CN)) {
                l.add(FN);
            }
        }
        return l;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Node> nodes= cleanName2Nodes.get(locationName.toLowerCase().replaceAll("\\s+",""));
        List<Map<String, Object>> l = new ArrayList<>();
        for(Node n : nodes) {
            Map<String, Object> m = new HashMap<>();
            m.put("lat", n.lat());
            m.put("lon", n.lon());
            m.put("name", n.name());
            m.put("id", n.id());
            l.add(m);
        }
        return l;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
