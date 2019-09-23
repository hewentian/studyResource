package composite;

import java.util.List;

public class Test {
    public static void main(String[] args) {
        Employer projectManager = new ProjectManager("项目经理");
        Employer projectAssistant = new ProjectAssistant("项目助理");
        Employer programmer1 = new Programmer("程序员1");
        Employer programmer2 = new Programmer("程序员2");

        projectManager.add(projectAssistant); // 为 项目经理 添加 项目助理
        projectManager.add(programmer1);      // 为 项目经理 添加 程序员1
        projectManager.add(programmer2);      // 为 项目经理 添加 程序员2

        List<Employer> employers = projectManager.getEmployers();
        for (Employer employer : employers) {
            System.out.println(employer.getName());
        }
    }
}
