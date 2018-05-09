package finalProject;

import java.util.ArrayList;

public class Skeletons {
private ArrayList skeletons = new ArrayList<Skeleton>();

public ArrayList<Skeleton> getList(){
	return skeletons;
}

public void remove(int index){
	skeletons.remove(index);
}

public void add(Skeleton s){
	skeletons.add(s);
}

}
