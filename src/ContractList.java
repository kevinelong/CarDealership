import java.util.ArrayList;

public class ContractList {
    private ArrayList<BusinessContract> list;
    ContractList(){
        list = new ArrayList<>();
    }
    public void addContract(BusinessContract bc){
        list.add(bc);
    }
    ArrayList<BusinessContract> getList(){
        return list;
    }
    ArrayList<SalesContract> getListSales(){
        ArrayList<SalesContract> output = new ArrayList<>();
        for(BusinessContract bc : list){
            if(bc instanceof SalesContract){
                output.add((SalesContract) bc);
            }
        }
        return output;
    }
    ArrayList<LeaseContract> getListLeases(){
        ArrayList<LeaseContract> output = new ArrayList<>();
        for(BusinessContract bc : list){
            if(bc instanceof LeaseContract){
                output.add((LeaseContract) bc);
            }
        }
        return output;
    }
}
