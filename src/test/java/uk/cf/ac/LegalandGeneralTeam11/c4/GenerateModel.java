package uk.cf.ac.LegalandGeneralTeam11.c4;

import com.structurizr.Workspace;
import com.structurizr.analysis.ComponentFinder;
import com.structurizr.analysis.ReferencedTypesSupportingTypesStrategy;
import com.structurizr.analysis.SpringComponentFinderStrategy;
import com.structurizr.analysis.StructurizrAnnotationsComponentFinderStrategy;
import com.structurizr.api.StructurizrClient;
import com.structurizr.model.*;
import com.structurizr.view.*;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import com.structurizr.importer.documentation.AdrToolsDecisionImporter;

import java.io.File;

public class GenerateModel {
    private final static int WORKSPACE_ID = 88542;
    private final static String API_KEY = "99b29289-2057-4f2c-a118-1a72c81403cb";
    private final static String API_SECRET = "d934ea5b-069f-4148-b802-1b419d899426";


    @Test
    public void generateModel() throws Exception {

        Workspace workspace = new Workspace("Legal ang general", "model for the legal and general 360 assesment project");

        Model model = workspace.getModel();


        SoftwareSystem LegalAndGeneral360Assesment = model.addSoftwareSystem("Legal ang general",
                "a system that automates the process of 360 assessment");

        Person admin = model.addPerson("admin", "A superuser who manages the system");
        Person employee = model.addPerson("employee", "person to be assessed.");
        Person manager = model.addPerson("Manager", " a manager to an employee.");
        admin.uses(LegalAndGeneral360Assesment, "Uses");
        employee.uses(LegalAndGeneral360Assesment, "Uses");
        manager.uses(LegalAndGeneral360Assesment, "Uses");

        Container webApplication = LegalAndGeneral360Assesment.addContainer("Spring Boot Application", "The web application", "Embedded web container. Tomcat 7.0");
        Container relationalDatabase = LegalAndGeneral360Assesment.addContainer("Relational Database", "Stores information regarding the assessment.", "MySQL");
        admin.uses(webApplication, "Uses", "HTTPs");
        employee.uses(webApplication, "Uses", "HTTPs");
        manager.uses(webApplication, "Uses", "HTTPs");
        webApplication.uses(relationalDatabase, "Reads from and writes to", "JDBC, port 3306");



        ComponentFinder componentFinder = new ComponentFinder(
                webApplication,
                "uk.ac.LegalandGeneralTeam11",
                new SpringComponentFinderStrategy(
                        new ReferencedTypesSupportingTypesStrategy()
                ));

        componentFinder.findComponents();


        ComponentFinder componentFinderByAnnotation = new ComponentFinder(
                webApplication,
                "uk.ac.LegalandGeneralTeam11",
                new StructurizrAnnotationsComponentFinderStrategy()
        );
        componentFinderByAnnotation.findComponents();

        webApplication.getComponents().stream()
                .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
                .forEach(c -> admin.uses(c, "Uses", "HTTP"));

        // connect the registration system to all of the Spring MVC controllers
        webApplication.getComponents().stream()
                .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
                .forEach(c -> employee.uses(c, "Uses", "HTTP"));

        // connect the registration system to all of the Spring MVC controllers
        webApplication.getComponents().stream()
                .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER))
                .forEach(c -> manager.uses(c, "Uses", "HTTP"));








        webApplication.getComponents().stream()
                .filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY))
                .forEach(c -> c.uses(relationalDatabase, "Reads from and writes to", "JDBC"));

        //Let's see what is being scanned
        for (Component c : webApplication.getComponents()) {
            System.out.println(c.getRelationships());
        }




        ViewSet viewSet = workspace.getViews();
        SystemContextView contextView = viewSet.createSystemContextView(LegalAndGeneral360Assesment, "context", "The System Context diagram for Legal and general 360 assesment system.");
        contextView.addAllSoftwareSystems();
        contextView.addAllPeople();
        contextView.enableAutomaticLayout();

        ContainerView containerView = viewSet.createContainerView(LegalAndGeneral360Assesment, "containers", "The Containers diagram for Legal and general 360 assesment system.");
        containerView.addAllPeople();
        containerView.addAllSoftwareSystems();
        containerView.addAllContainers();
        containerView.enableAutomaticLayout();

        ComponentView componentView = viewSet.createComponentView(webApplication, "components", "The Components diagram for Legal and general 360 assesment system.");
        componentView.addAllComponents();
        componentView.addAllPeople();
        componentView.add(relationalDatabase);
        componentView.enableAutomaticLayout();







       for (Component component : webApplication.getComponents()) {
            for (CodeElement codeElement : component.getCode()) {
                String sourcePath = codeElement.getUrl();
                if (sourcePath != null) {
                    codeElement.setUrl(
                            "https://git.cardiff.ac.uk/c22005519/client-project-team-11/-/tree/main");
                }
            }
        }

        // rather than creating a component model for the database, let's simply link to the DDL
        // (this is really just an example of linking an arbitrary element in the model to an external resource)
        relationalDatabase.setUrl("https://github.com/spring-projects/spring-petclinic/tree/master/src/main/resources/db/hsqldb");

        // tag and style some elements





        LegalAndGeneral360Assesment.addTags("Legal and general 360 assessment system");
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER)).forEach(c -> c.addTags("Spring MVC Controller"));
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_MVC_CONTROLLER)).forEach(c -> c.addTags("Spring REST Controller"));

        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_SERVICE)).forEach(c -> c.addTags("Spring Service"));
        webApplication.getComponents().stream().filter(c -> c.getTechnology().equals(SpringComponentFinderStrategy.SPRING_REPOSITORY)).forEach(c -> c.addTags("Spring Repository"));
        relationalDatabase.addTags("Database");


        Styles styles = viewSet.getConfiguration().getStyles();
        styles.addElementStyle("Legal and general 360 assessment system").background("#6CB33E").color("#ffffff");
        styles.addElementStyle(Tags.PERSON).background("#519823").color("#ffffff").shape(Shape.Person);
        styles.addElementStyle(Tags.CONTAINER).background("#91D366").color("#ffffff");
        styles.addElementStyle("Database").shape(Shape.Cylinder);

        styles.addElementStyle("Spring REST Controller").background("#D4FFC0").color("#000000");

        styles.addElementStyle("Spring MVC Controller").background("#D4F3C0").color("#000000");
        styles.addElementStyle("Spring Service").background("#6CB33E").color("#000000");
        styles.addElementStyle("Spring Repository").background("#95D46C").color("#000000");


        // add ADRs

        File adrDirectory = new File("./src/main/adr");
        AdrToolsDecisionImporter decisionImporter = new AdrToolsDecisionImporter();
        decisionImporter.importDocumentation(LegalAndGeneral360Assesment, adrDirectory);

        System.setProperty("jdk.tls.client.protocols","TLSv1,TLSv1.1,TLSv1.2");


        StructurizrClient structurizrClient = new StructurizrClient(API_KEY, API_SECRET);
        structurizrClient.putWorkspace(WORKSPACE_ID, workspace);







    }


}
