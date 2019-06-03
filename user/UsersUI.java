package xupt.se.ttms.view.user;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xupt.se.ttms.model.Employee;
import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.LoginedUser;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.Plan.PlanArangeUI;
import xupt.se.ttms.view.studio.ManageStudio;
import xupt.se.ttms.view.studio.ProcessStudioUI;

import java.util.List;

public class UsersUI extends Application {

    Employee users ;
    Scene sne ;
    List<Employee>list ;
    GridPane grid ;
    Stage userWindow ;
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    public void setGrid(){
        grid.setHgap(90);
        grid.setVgap(10);
        grid.add(new Text("用户ID"), 0,1) ;
        grid.add(new Text("权限"), 1,1) ;
        grid.add(new Text("用户名"), 2,1) ;
        grid.add(new Text("电话号码"), 3,1) ;
        int i = 2, j= 0 ;
        list = LoginedUser.getAllUser() ;
        for(Employee uu: list) {

            Button bt = new Button("删除") ;
            Button modiButton = new Button("修改") ;
            grid.add(new Text(uu.getId()+""),j,i ) ;
            if(uu.getAccess() == 0)
                grid.add(new Text("管理员"), j+1,i) ;
            else if(uu.getAccess() == 1)
                grid.add(new Text("经理"), j+1,i) ;
            else if(uu.getAccess() == 2)
                grid.add(new Text("售票员"), j+1,i) ;
            else {
                grid.add(new Text("售票员"), j+1,i) ;
            }
            grid.add(new Text(uu.getName()), j+2,i) ;
            grid.add(new Text(uu.getTel()), j+3, i) ;
            grid.add(bt, j+4, i) ;
            grid.add(modiButton, j+5, i) ;
            i++ ;
            bt.setOnAction(e->{
                delUser(uu) ;
            });
            modiButton.setOnAction(e->{
                modifyUserInfo(uu) ;
            });
        }
    }

    //销售员操作
    public void getSaleSne(Employee user, Stage win) {
        ManageStudio  ms = new ManageStudio(user, win) ;
        return ;
    }

    //经理操作
    public void getManagerSne(Employee user, Stage win) {

    }

    public void getStuioManagerSne(Employee user, Stage win) {
        userWindow = win ;
        Scene s = getStudioSne(user) ;
        s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        win.setScene(s);
    }

    public Scene getStudioSne(Employee user) {
        users = user ;
        BorderPane bord = new BorderPane();
        bord.setPadding(new Insets(20,20,20,20));
        updateScene(user, bord) ;
        Scene sne = new Scene(bord, 1200,1000) ;
        return sne ;
    }

    public void updateScene(Employee user, BorderPane bord) {
        VBox vb = new VBox() ;
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10));
        vb.setSpacing(10);

        Label lb = new Label("演出厅管理"+" "+"当前在线经理："+user.getName()+" "+"经理ID:"+user.getId());
        lb.setStyle("-fx-background-color: #987;-fx-font-size:20");

        GridPane gp = new GridPane() ;
        gp.setStyle("-fx-background-color:#EBF5E4; -fx-border-color: #B4D6A9;");
        gp.setHgap(60) ;
        gp.setVgap(10) ;
        gp.setPadding(new Insets(20,20,20,20));

        StudioSrv ss = new StudioSrv() ;
        List<Studio>list = ss.FetchAll() ;
        Label Id =new  Label("演出厅ID") ;
        Label name = new Label("名称");
        Label row = new Label("排数");
        Label column = new Label("列数");
        Label intro = new Label("介绍");

        gp.add(new Text("演出厅ID"), 1,1) ;
        gp.add(new Text("名称"), 2,1);
        gp.add(new Text("排数"),3,1);
        gp.add(new Text("列数"),4,1);
        gp.add(new Text("介绍"),5,1);
        int cols = 1;
        int rows = 2 ;
        for(Studio s : list) {
            Button delete = new Button("删除");
            Button modify = new Button("修改");
            delete.setOnMouseClicked(e->{
                deleteStudio(s);
            });

            modify.setOnMouseClicked(e->{
                modifyStudio(s) ;
            });
            delete.setMinSize(100,30);
            modify.setMinSize(100,30);

            gp.add(new Text(s.getID()+""), cols, rows) ;
            gp.add(new Text(s.getName()+""), cols+1, rows);
            gp.add(new Text(s.getRowCount()+""), cols+2,rows) ;
            gp.add(new Text(s.getColCount()+""), cols+3,rows) ;
            gp.add(new Text(s.getIntroduction()+""), cols+4,rows) ;
            gp.add(delete, cols+5, rows) ;
            gp.add(modify, cols+6, rows) ;
            rows++ ;
        }

        vb.getChildren().addAll(lb, gp) ;

        Button add = new Button("添加演出厅") ;
        add.setOnMouseClicked(e->{
            addStudio() ;
        });
        add.setMinSize(150,40);
        Button quit = new Button("退出") ;
        quit.setOnMouseClicked(e->{
            userWindow.close() ;
        });
        quit.setMinSize(150,40);
        Button managemv = new Button("剧目管理") ;
        managemv.setMinSize(150,40);
        managemv.setOnMouseClicked(e->{

        });

        Button refresh =  new Button("刷新页面") ;
        refresh.setOnMouseClicked(e->{
            Scene s = getStudioSne(users) ;
            userWindow.setScene(s);
            s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
            userWindow.show() ;
        });
        managemv.setMinSize(150,40);
        Button plan = new Button("安排演出计划");
        plan.setMinSize(150,40);

        plan.setOnMouseClicked(e->{
            userWindow.setTitle("安排演出计划");
            PlanArangeUI plans = new PlanArangeUI();
            plans.getPlanSne(userWindow, user) ;
        });

        HBox hb = new HBox() ;
        hb.setPadding(new Insets(20,20,20,20)) ;
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(add, quit, managemv, refresh, plan);


        bord.setTop(vb);
        bord.setBottom(hb);
    }

    public void addStudio() {
        ProcessStudioUI ps = new ProcessStudioUI() ;
        ps.addStudioUI();
    }

    public void modifyStudio(Studio s) {
        ProcessStudioUI mod = new ProcessStudioUI() ;
        mod.modifyStudioUI(s);
    }

    public void deleteStudio(Studio s) {
        StudioSrv studio = new StudioSrv() ;
        studio.delete(s.getID()) ;
        Scene ss = getStudioSne(users) ;
        userWindow.setScene(ss);
        ss.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        userWindow.show() ;
    }

    //管理员操作
    public void getUserAdminSne(Employee user, Stage stage){
        users = user ;
        userWindow = stage ;
        //放置button的地方
        userWindow.setScene(updateScene(user));
        sne.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        userWindow.setScene(sne);
    }

    public void delUser(Employee uu) {
        LoginedUser user = new LoginedUser() ;
        int ret = user.delete(uu.getId()) ;
        if(ret != 0) {
            Scene s = updateScene(uu) ;
            sne.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
            userWindow.setScene(s);
            userWindow.show() ;
        }
        else {
            ConfirmBox con = new ConfirmBox() ;
            con.displaySuccess("删除失败！请重试一下！");
        }
    }

    //修改用户信息
    public void modifyUserInfo(Employee user){

        Scene s = updateScene(user) ;
        sne.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
        userWindow.setScene(s);
        userWindow.show() ;
    }

    public Scene updateScene(Employee user) {
        //放置button的地方
        BorderPane bord = new BorderPane() ;

        HBox hb = new HBox() ;
        VBox vb = new VBox() ;

        vb.setSpacing(50);
        vb.setPadding(new Insets(20,20,20,20)) ;
        vb.setAlignment(Pos.CENTER);
        Label lb = new Label("用户管理     管理员名称："+user.getName());
        lb.setStyle("-fx-font-size:50");
        lb.setStyle("-fx-background-color: #C75F80");
        HBox hbLb = new HBox() ;
        hbLb.setAlignment(Pos.CENTER);
        hbLb.getChildren().add(lb) ;

        grid = new GridPane() ;
        setGrid();
        vb.getChildren().addAll(hbLb, grid);
        bord.setTop(vb);

        grid.setId("gridpane") ;
        grid.setAlignment(Pos.CENTER);

        Button addButton = new Button("添加用户") ;
        Button update = new Button("刷新页面") ;
        Button quit = new Button("退  出") ;

        setButton(addButton ,quit, update) ;
        hb.setSpacing(50);
        hb.setPadding(new Insets(20,20,100,100));
        hb.setAlignment(Pos.CENTER);
        hb.getChildren().addAll(addButton,quit, update) ;
        bord.setBottom(hb);
        sne = new Scene(bord, 1200, 1000) ;
        return sne ;
    }

    void setButton(Button addButton ,Button quit, Button update) {
        addButton.setMinWidth(150);
//        fetchUser.setMinWidth(150);
        quit.setMinWidth(150);
        update.setMinWidth(150);
        addButton.setOnMouseClicked(e->{
            UserSceneUI sce = new UserSceneUI();
            Employee uu = new Employee() ;
            sce.addUser(uu);
            Scene s  = updateScene(users);
            s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
            userWindow.setScene(s);
            userWindow.show();
        });

        update.setOnAction(e->{
            Scene s = updateScene(users) ;
            s.getStylesheets().add(LoginUI.class.getResource("Style.css").toExternalForm());
            userWindow.setScene(s);
            userWindow.show() ;
        });


    }
}