package chainofresponsibility;

public class HRRequestHandle implements RequestHandle {
    @Override
    public void handleRequest(Request request) {
        if (request instanceof DimissionRequest) {
            System.out.println("要离职，人事部门审批");
        } else {
            System.out.println("找不到处理该请求的处理器");
        }
    }
}
