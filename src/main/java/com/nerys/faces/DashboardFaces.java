/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.faces;
// <editor-fold defaultstate="collapsed" desc="imports">

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbCoreContext;
import com.avbravo.jmoordbutils.JmoordbCoreXHTMLUtil;
import com.avbravo.jmoordbutils.JmoordbCronometer;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaContext;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaManager;
import com.avbravo.jmoordbutils.paginator.IPaginator;
import com.avbravo.jmoordbutils.paginator.Paginator;
import com.avbravo.jmoordbutils.websocket.MessageWebSocket;
import com.jmoordb.core.model.Pagination;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import com.nerys.commons.emails.EmailSender;
import com.nerys.commons.emails.EmailSenderEvent;
import com.nerys.converter.services.DepartamentViewConverterServices;
import com.nerys.converter.services.IconoConverterServices;
import com.nerys.converter.services.UserViewConverterServices;
import com.nerys.faces.services.DashboardFacesServices;
import com.nerys.faces.services.FacesServices;
import com.nerys.model.Profile;
import com.nerys.model.Proyecto;
import com.nerys.model.User;
import com.nerys.model.UserView;
import com.nerys.services.ProyectoServices;
import com.nerys.services.UserViewServices;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.primefaces.model.ResponsiveOption;
import com.nerys.services.ColorManagement;
import com.nerys.services.IconoServices;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import com.nerys.restclient.UserRestClient;
import com.nerys.restclient.ProyectoRestClient;
import com.nerys.services.ClienteServices;
import com.nerys.services.DepartamentViewServices;
import com.nerys.services.UserServices;
import com.nerys.websocket.fire.FacesWebSocket;
import com.nerys.websocket.fire.WebSocketController;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.event.Event;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.primefaces.PrimeFaces;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CloseEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.file.UploadedFile;
// </editor-fold>

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
@Data
public class DashboardFaces implements Serializable, JmoordbCoreXHTMLUtil, IPaginator, FacesWebSocket, FacesServices {
// <editor-fold defaultstate="collapsed" desc="WebSocket">

    @Inject
    WebSocketController webSocketController;
    private Boolean dialogVisibleWebSocket = Boolean.FALSE;
    private Boolean dialogVisibleAddWebSocket = Boolean.FALSE;
    private List<UserView> userViewForWebSocketList = new ArrayList<>();
    private Integer contadorNotificacionesWebsocket = 0;
    private Long jmoordbCronometerOld = 0L;

    // </editor-fold>  
    // <editor-fold defaultstate="collapsed" desc="ConverterServices">


    @Inject
    DepartamentViewConverterServices departamentViewConverterServices;

   
    @Inject
    IconoConverterServices iconoConverterServices;
    @Inject
    UserViewConverterServices userViewConverterServices;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="LazyDataModel">
    
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" fields">
    private static final long serialVersionUID = 1L;
    private User userLogged = new User();
 Long totalClientes = 0L;
 Long totalProyectos = 0L;

    private Long totalOpenParaArchivarProyecto = 0L;
    private Long totalColumnaPendienteParaArchivarProyecto = 0L;
    private Long totalColumnaProgresoArchivarProyecto = 0L;

    private List<ResponsiveOption> responsiveOptions;
    private Boolean showDetallesProyecto = Boolean.FALSE;
    private Boolean isOverlayPanelOpen = Boolean.FALSE;
    private Boolean isButtonSavePressed = Boolean.TRUE;
    private Boolean fueCambiadoPorOtroUsuario = Boolean.FALSE;
    private Boolean fueCambiadoPorOtroUsuarioUser = Boolean.FALSE;
    private Boolean showDialogContent = Boolean.TRUE;
    private Boolean showDialogContentUser = Boolean.TRUE;
    private List<String> diasLaborableList;
    private String passwordRepetido = "";
    private String message = "";

    private Profile profileLogged = new Profile();

    // Invocados desde otro fomulario
    private Boolean isEditable = Boolean.TRUE;
    private String callerLevel0 = "";

  
//    private Sprint sprintSelected = new Sprint();
    private User userSelected = new User();

    private User userDB = new User();
    private List<UserView> userViewList = new ArrayList<>();

    private List<UserView> userViewPropietarioNewSelectedList = new ArrayList<>();
    private List<UserView> userViewColaboradorNewSelectedList = new ArrayList<>();
    private List<UserView> userViewPropietarioEditSelectedList = new ArrayList<>();
    private List<UserView> userViewColaboradorEditSelectedList = new ArrayList<>();






    private List<Profile> profileList = new ArrayList<>();
//    private List<Tarjeta> tarjetaViewDashboard = new ArrayList<>();

  
    /**
     * Password
     */
    Boolean alMenos9Caracteres = Boolean.FALSE;
    Boolean alMenosLetraMinuscula = Boolean.FALSE;
    Boolean alMenosLetraMayuscula = Boolean.FALSE;
    Boolean alMenosUnNumero = Boolean.FALSE;
    Boolean alMenosCaracterEspecial = Boolean.FALSE;

    private UploadedFile file;
    private Boolean fileWasUploaded = false;
    private Boolean isFileImagen = Boolean.FALSE;
    private String fileRepositoryDirectory = "";
    Integer totalRecords = 0;
    private Boolean isRowPageSmall = Boolean.TRUE;
    private DataTable dataTable;
    private AutoComplete autoComplete;
    
    private String textToSearch = "";
    private String textDescripcionToSearch = "";
    private Boolean includeUser = Boolean.FALSE;
    private Boolean includePrioridad = Boolean.FALSE;
    private Boolean includeColumna = Boolean.FALSE;
    private Boolean includeProject = Boolean.FALSE;
    private Boolean includeDeposito = Boolean.FALSE;
    private Boolean includeRango = Boolean.FALSE;
    private String filterText = "";
    private String depositoSelected = "";
    private String columnaSelected = "";


    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="paginator ">
    Paginator paginator = new Paginator();
    Paginator paginatorOld = new Paginator();

    Pagination paginationAutocompleteTartjeta = new Pagination();

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="selected For Dialog()">
//    public Sprint getSprintSelected() {
//        if (sprintSelected == null) {
//            sprintSelected = new Sprint();
//        }
//        return sprintSelected;
//    }
   

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    JmoordbResourcesFiles rf;
    @Inject
    JmoordbCoreMediaManager jmoordbCoreMediaManager;
    @Inject
    JmoordbCoreMediaContext jmoordbCoreMediaContext;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Event">
    @Inject
    Event<EmailSenderEvent> emailSenderEvent;
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Services">
    
    @Inject
    ClienteServices clienteServices;
  
    @Inject
    ColorManagement colorManagement;
    @Inject
    DashboardFacesServices dashboardFacesServices;
    @Inject
    DepartamentViewServices departamentViewServices;
    
    @Inject
    IconoServices iconoServices;


@Inject
    ProyectoServices proyectoServices;
  
    @Inject
    UserServices userServices;
    @Inject
    UserViewServices userViewServices;
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SecretKey()">
    private String secretKey = "SCox1jmWrkma$*opne2Amwz";
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Microprofile Config">
    @Inject
    private Config config;
    @Inject
    @ConfigProperty(name = "idapplicative")
    private Provider<Integer> idapplicative;
    @Inject
    @ConfigProperty(name = "loginStyle")
    private Provider<String> loginStyle;
    @Inject
    @ConfigProperty(name = "application.version")
    private Provider<String> applicationVersion;
    @Inject
    @ConfigProperty(name = "smallSizeOfTextForCut")
    private Provider<Integer> smallSizeOfTextForCut;
    @Inject
    @ConfigProperty(name = "mediumSizeOfTextForCut")
    private Provider<Integer> mediumSizeOfTextForCut;
    @Inject
    @ConfigProperty(name = "largeSizeOfTextForCut")
    private Provider<Integer> largeSizeOfTextForCut;
    @Inject
    @ConfigProperty(name = "showEstadisticasProyectoDashboardPrincipal")
    private Provider<Boolean> showEstadisticasProyectoDashboardPrincipal;
    @Inject
    @ConfigProperty(name = "showNotificacionesDashboardPrincipal")
    private Provider<Boolean> showNotificacionesDashboardPrincipal;
    @Inject
    @ConfigProperty(name = "showMiembrosEquipoDashboardPrincipal")
    private Provider<Boolean> showMiembrosEquipoDashboardPrincipal;
    @Inject
    @ConfigProperty(name = "showTimelineDashboardPrincipal")
    private Provider<Boolean> showTimelineDashboardPrincipal;

    // Converter
    @Inject
    @ConfigProperty(name = "converter.max.number.of.elements")
    private Provider<Integer> converterMaxNumberOfElements;
// #Websocket    
    @Inject
    @ConfigProperty(name = "websocket.minimums.seconds.for.update")
    private Provider<Integer> websocketMinimumsSecondsForUpdate;
    // #--Path Images
    @Inject
    @ConfigProperty(name = "pathBaseLinuxAddUserHome", defaultValue = "true")
    private Provider<Boolean> pathBaseLinuxAddUserHome;

    @Inject
    @ConfigProperty(name = "pathBaseLinuxAddUserHomeStoreInCollections", defaultValue = "false")
    private Provider<Boolean> pathBaseLinuxAddUserHomeStoreInCollections;

    @Inject
    @ConfigProperty(name = "pathLinux", defaultValue = " ")
    private Provider<String> pathLinux;

    @Inject
    @ConfigProperty(name = "pathWindows", defaultValue = " ")
    private Provider<String> pathWindows;

    @Inject
    @ConfigProperty(name = "pathLinuxFileRepository", defaultValue = " ")
    private Provider<String> pathLinuxFileRepository;

    @Inject
    @ConfigProperty(name = "pathWindowsFileRepository", defaultValue = " ")
    private Provider<String> pathWindowsFileRepository;

    // Row
    @Inject
    @ConfigProperty(name = "rowPage", defaultValue = "0")
    private Provider<Integer> rowPage;
    @Inject
    @ConfigProperty(name = "rowPageSmall", defaultValue = "0")
    private Provider<Integer> rowPageSmall;

    @Inject
    @ConfigProperty(name = "rowPageDialog", defaultValue = "0")
    private Provider<Integer> rowPageDialog;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Microprofile Rest Client">
    @Inject
    ProyectoRestClient proyectoClient;

    @Inject
    UserRestClient userClient;
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc=" init">
    @PostConstruct
    public void init() {
        try {
            JmoordbCronometer.startCronometer(FacesUtil.nameOfClassAndMethod());
totalClientes = 0L;
totalProyectos=0L; 
            includeUser = Boolean.TRUE;
            includeColumna = Boolean.TRUE;
            includeProject = Boolean.FALSE;
            message = "";
            columnaSelected = rf.fromMessage("selectonemenu.pendienteValue");
            depositoSelected = "";
            filterText = "";
            /**
             * WebSocket
             */
            contadorNotificacionesWebsocket = 0;
            jmoordbCronometerOld = 0L;
            dialogVisibleWebSocket = Boolean.FALSE;
            dialogVisibleAddWebSocket = Boolean.FALSE;

            userLogged = (User) JmoordbCoreContext.get("LoginFaces.userLogged");
            profileLogged = (Profile) JmoordbCoreContext.get("LoginFaces.profileLogged");
            profileList = new ArrayList<>();
            diasLaborableList = new ArrayList<>();
            diasLaborableList.add(rf.fromMessage("field.lunes"));
            diasLaborableList.add(rf.fromMessage("field.martes"));
            diasLaborableList.add(rf.fromMessage("field.miercoles"));
            diasLaborableList.add(rf.fromMessage("field.jueves"));
            diasLaborableList.add(rf.fromMessage("field.viernes"));
            diasLaborableList.add(rf.fromMessage("field.sabado"));
            diasLaborableList.add(rf.fromMessage("field.domingo"));
            fileRepositoryDirectory = FacesUtil.pathOfMicroprofileConfig(pathBaseLinuxAddUserHome.get(), pathLinuxFileRepository.get(), pathWindowsFileRepository.get());
            refresh();
//            loadIcono();

            //Cargar el media type
            saveToMediaContext(userLogged.getPhoto());
            responsiveOptions = new ArrayList<>();
            responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
            responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
            responsiveOptions.add(new ResponsiveOption("560px", 1, 1));

//            findByTarjetas();
//            this.tarjetaLazyDataModel = new LazyDataModel<Tarjeta>() {
//                @Override
//                public List<Tarjeta> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
//
//                    if (textDescripcionToSearch == null || textDescripcionToSearch.equals("")) {
//                        totalRecords = tarjetaServices.searchCountLikeByTarjeta(textToSearch, paginator.getFilter(), paginator.getSort(), paginator.getPage(), rowPageSmall.get()).intValue();
//                    } else {
//                        if ((textToSearch == null || textToSearch.equals("")) && (textDescripcionToSearch != null || !textDescripcionToSearch.equals(""))) {
//
//                            totalRecords = tarjetaServices.searchCountLikeByDescripcion(textDescripcionToSearch, paginator.getFilter(), paginator.getSort(), paginator.getPage(), rowPageSmall.get()).intValue();
//                        } else {
//
//                            totalRecords = tarjetaServices.searchCountLikeByTarjeta(textToSearch, paginator.getFilter(), paginator.getSort(), paginator.getPage(), rowPageSmall.get()).intValue();
//                        }
//                    }
//
//                    List<Paginator> list = new ArrayList<>();
//                    if (!isRowPageSmall) {
//
//                        /**
//                         * Utiliza rowPage
//                         */
//                        list = processLazyDataModel(paginator, paginatorOld, offset, rowPage.get(), totalRecords, sortBy);
//
//                    } else {
//
//                        /**
//                         * Utiliza rowPageWithOverlayPanel para el OverlayPanel
//                         */
//                        list = processLazyDataModel(paginator, paginatorOld, offset, rowPageSmall.get(), totalRecords, sortBy);
//
//                    }
////
//
//                    paginator = list.get(0);
//                    paginatorOld = list.get(1);
//                    Pagination pagination = new Pagination();
//                    if (!isRowPageSmall) {
//
//                        paginator.setNumberOfPage(numberOfPages(totalRecords, rowPage.get()));
//                        pagination = new Pagination(paginator.getPage(), rowPage.get());
//                    } else {
//
//                        paginator.setNumberOfPage(numberOfPages(totalRecords, rowPageSmall.get()));
//                        pagination = new Pagination(paginator.getPage(), rowPageSmall.get());
//                    }
//
//                    List<Tarjeta> result = new ArrayList<>();
//
//                    if (textDescripcionToSearch == null || textDescripcionToSearch.equals("")) {
//                        result = tarjetaServices.searchLikeByTarjeta(textToSearch, paginator.getFilter(), paginator.getSort(), paginator.getPage(), rowPageSmall.get());
//                    } else {
//                        if ((textToSearch == null || textToSearch.equals("")) && (textDescripcionToSearch != null || !textDescripcionToSearch.equals(""))) {
//                            result = tarjetaServices.searchLikeByDescripcion(textDescripcionToSearch, paginator.getFilter(), paginator.getSort(), paginator.getPage(), rowPageSmall.get());
//                        } else {
//                            result = tarjetaServices.searchLikeByTarjeta(textToSearch, paginator.getFilter(), paginator.getSort(), paginator.getPage(), rowPageSmall.get());
//                        }
//                    }
//
//                    tarjetaLazyDataModel.setRowCount(totalRecords);
//
//                    PrimeFaces.current().executeScript("setDataTableWithPageStart()");
//                    PrimeFaces.current().executeScript("widgetVardataTable.getPaginator().setPage(0);");
//                    tarjetaList = result;
//
//                    return result;
//                }
//
//                @Override
//                public int count(Map<String, FilterMeta> map) {
//
//                    return totalRecords;
//
//                }
//
//                @Override
//                public String getRowKey(Tarjeta object) {
//                    if (object == null || object.getIdtarjeta() == null) {
//                        return "";
//                    }
//                    return object.getIdtarjeta().toString();
//                }
//
//                @Override
//                public Tarjeta getRowData(String rowKey) {
//                    for (Tarjeta t : tarjetaList) {
//                        if (t != null) {
//                            if (t.getIdtarjeta().equals(rowKey)) {
//                                return t;
//                            }
//                        }
//                    }
//                    return null;
//                }
//
//            };

            JmoordbCronometer.endCronometer(FacesUtil.nameOfClassAndMethod(), "\t\t userLogged.getName()" + userLogged.getName());

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void preDestroy()">
    /**
     *
     */
    @PreDestroy
    public void preDestroy() {
      
        departamentViewConverterServices.destroyed();
      
        iconoConverterServices.destroyed();
        userViewConverterServices.destroyed();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String refresh()">

    @Override
    public String refresh() {
        try {
            totalClientes = 0L;
            totalProyectos =0L;

            textToSearch = "";
            textDescripcionToSearch = "";
            contadorNotificacionesWebsocket = 0;
            dialogVisibleWebSocket = Boolean.FALSE;
            dialogVisibleAddWebSocket = Boolean.FALSE;
            userViewList = new ArrayList<>();
            
            
            
            

       
            message = "";
            if (showMiembrosEquipoDashboardPrincipal.get()) {
               
            }

          if (showEstadisticasProyectoDashboardPrincipal.get()) {
             Bson filter = eq("active", Boolean.TRUE);
                        Document sort = new Document("idcliente", 1);
       
                
            totalClientes  = clienteServices.count(filter, sort, 0, 0);
            
             Bson filterProyectos = and(eq("active", Boolean.TRUE),
                     eq("departament.iddepartament",profileLogged.getDepartamentView().getIddepartament())
                     );
                        Document sortProyectos = new Document("idproyecto", 1);
                        
            totalProyectos = proyectoServices.count(filterProyectos,sortProyectos,0,0);

               
            }

            if (showNotificacionesDashboardPrincipal.get()) {
                /**
                 * Cargar la lista de notificaciones
                 */

            }

            if (showTimelineDashboardPrincipal.get()) {
                /**
                 * Carga el timeline el ultimo evento de cada proyecto
                 */
             

            }

            PrimeFaces.current().ajax().update("form");
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Boolean showEstadistica()">
    public Boolean showEstadistica() {
        Boolean result = Boolean.TRUE;
        try {

            if (showEstadisticasProyectoDashboardPrincipal == null) {

            } else {

                return showEstadisticasProyectoDashboardPrincipal.get();
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean showNotificacion()">

    public Boolean showNotificaciones() {
        Boolean result = Boolean.TRUE;
        try {

            if (showNotificacionesDashboardPrincipal == null) {

            } else {

                return showNotificacionesDashboardPrincipal.get();
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean showMiembrosEquipo()">

    public Boolean showMiembrosEquipo() {
        Boolean result = Boolean.TRUE;
        try {

            if (showMiembrosEquipoDashboardPrincipal == null) {

            } else {

                return showMiembrosEquipoDashboardPrincipal.get();
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Boolean showTimeline()">

    public Boolean showTimeline() {
        Boolean result = Boolean.TRUE;
        try {

            if (showTimelineDashboardPrincipal == null) {

            } else {

                return showTimelineDashboardPrincipal.get();
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String saveToMediaContext(String pathOfFile, String... nameOfFile)">
    public String saveToMediaContext(String pathOfFile, String... nameOfFile) {
        try {
            String name = pathOfFile;

            if (nameOfFile.length != 0) {
                name = nameOfFile[0];
            }

            if (!pathBaseLinuxAddUserHomeStoreInCollections.get()) {
                pathOfFile = FacesUtil.userHome() + pathOfFile;

            }

            jmoordbCoreMediaContext.put("pathOfFile", pathOfFile);
            jmoordbCoreMediaContext.put("nameOfFile", name);
            jmoordbCoreMediaManager.init();

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return "";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String repairPathOfFile(String pathOfFile)">
    public String repairPathOfFile(String pathOfFile) {
        try {
            if (!pathBaseLinuxAddUserHomeStoreInCollections.get()) {
                pathOfFile = FacesUtil.userHome() + pathOfFile;
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return pathOfFile;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String knobColor(Proyecto proyecto)">
    public String knobColor(Proyecto proyecto) {
        String result = "black";
        try {

//            result = colorManagement.color(proyecto.getAvance());
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String colorBarClassProyecto proyecto)">

    /**
     * Barras de cada tarjeta
     *
     * @param proyecto
     * @return
     */
    public String colorBarClass(Proyecto proyecto) {
        String result = "black";
        try {

//            result = colorManagement.colorBarClassDashboard(proyecto.getAvance());
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>
    
   
    
    

    // <editor-fold defaultstate="collapsed" desc="List<UserView> completeUserViewColaboradorNew(String query)">
    public List<UserView> completeUserViewColaboradorNew(String query) {

        List<UserView> result = new ArrayList<>();
        try {
            query = query.trim();

            result = dashboardFacesServices.loadUserViewHowColaboradoresNewOrEdit(query, userViewColaboradorNewSelectedList);

            userViewConverterServices.add(result.subList(0,
                    calcularConverterMaxNumberOfElements(result.size(), converterMaxNumberOfElements.get()))
            );
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="List<UserView> completeUserViewColaboradorEdit(String query)">
    public List<UserView> completeUserViewColaboradorEdit(String query) {

        List<UserView> result = new ArrayList<>();
        try {
            query = query.trim();

            result = dashboardFacesServices.loadUserViewHowColaboradoresNewOrEdit(query, userViewColaboradorEditSelectedList);
            userViewConverterServices.add(result.subList(0,
                    calcularConverterMaxNumberOfElements(result.size(), converterMaxNumberOfElements.get()))
            );

        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<UserView> completeUserViewPropietarioNew(String query)">

    public List<UserView> completeUserViewPropietarioNew(String query) {

        List<UserView> result = new ArrayList<>();
        try {
            query = query.trim();
            result = dashboardFacesServices.loadUserViewHowPropietarioNewOrEdit(query, userViewPropietarioNewSelectedList);

            userViewConverterServices.add(result.subList(0,
                    calcularConverterMaxNumberOfElements(result.size(), converterMaxNumberOfElements.get()))
            );

        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<UserView> completeUserViewPropietarioEdit(String query)">

    public List<UserView> completeUserViewPropietarioEdit(String query) {

        List<UserView> result = new ArrayList<>();
        try {
            query = query.trim();

            result = dashboardFacesServices.loadUserViewHowPropietarioNewOrEdit(query, userViewPropietarioEditSelectedList);
            userViewConverterServices.add(result.subList(0,
                    calcularConverterMaxNumberOfElements(result.size(), converterMaxNumberOfElements.get()))
            );
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="  autocompleteSelectedEvent(SelectEvent event)">

    public void autocompleteSelectedEvent(SelectEvent<UserView> event) {
        try {

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void autocompleteUnselectListener(UnselectEvent event)">
    public void autocompleteUnselectListener(UnselectEvent event) {

    }
    // </editor-fold>

   
   



   
   

    // <editor-fold defaultstate="collapsed" desc="void closeOverlayPanel(String panel) ">
    public void closeOverlayPanel(String panel) {
        try {
            PrimeFaces.current().executeScript(panel);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

   
    // <editor-fold defaultstate="collapsed" desc="String prepareNew()">

    public String prepareNew() {
        try {
            dialogVisibleWebSocket = Boolean.TRUE;
            dialogVisibleAddWebSocket = Boolean.TRUE;

          
            message = "";

            fueCambiadoPorOtroUsuario = Boolean.FALSE;
            
          

            userViewColaboradorNewSelectedList = new ArrayList<>();
            userViewPropietarioNewSelectedList = new ArrayList<>();
            UserView userView = new UserView(userLogged.getIduser(), userLogged.getName(), userLogged.getPhoto(), userLogged.getEmail(), userLogged.getRecibirNotificacion());
            userViewPropietarioNewSelectedList.add(userView);
            isOverlayPanelOpen = Boolean.TRUE;
            isButtonSavePressed = Boolean.TRUE;
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";

    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String prepareEditar(Proyecto proyecto)">

    public String prepareEditar(Proyecto proyecto) {
        try {

            dialogVisibleWebSocket = Boolean.TRUE;
           
         
           
            isOverlayPanelOpen = Boolean.TRUE;
            isButtonSavePressed = Boolean.TRUE;
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
   
   

    
    public void closeProjectDialog() {
        try {
            dialogVisibleAddWebSocket = Boolean.FALSE;
            closeOverlayPanel("PF('overlayPanelProyectoAgregar').hide()");
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }

 
  
 

    public void onItemUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

   
    // <editor-fold defaultstate="collapsed" desc="void sendEmailCreateProject(Proyecto proyecto)>
    public void sendEmailCreateProject(Proyecto proyecto) {
        try {

            List<String> emailColaboradorList = new ArrayList<>();
            List<String> emailPropietarioList = new ArrayList<>();

            /**
             *
             */
            Boolean found = Boolean.FALSE;
            /**
             * Recorre el old y busca los que se han removido
             */

//            for (ProyectoMiembro pm : proyecto.getProyectoMiembro()) {
//
//                if (pm.getUserView().getIduser().equals(userLogged.getIduser())) {
//                    // ya se le envio
//                } else {
//                    if (pm.getPropietario()) {
//                        emailPropietarioList.add(pm.getUserView().getEmail());
//                    } else {
//                        emailColaboradorList.add(pm.getUserView().getEmail());
//                    }
//
//                }
//            }

            String tituloEmail = "";
            String mensajeEmail = "";

            if (emailColaboradorList == null || emailColaboradorList.isEmpty()) {

            } else {

                tituloEmail = rf.fromMessage("emailtitulo.proyectocreado");

                mensajeEmail
                        = "<strong>" + rf.fromMessage("email.proyecto") + "</strong>" + " " + proyecto.getProyecto() + " <br>"
                        + "<strong>" + rf.fromMessage("email.enviadopor") + "</strong>" + " " + userLogged.getName() + "<br>"
                        + "<strong>" + rf.fromMessage("email.evento") + "</strong>" + " " + rf.fromMessage("email.asignadoaproyectocomocolaborador") + "<br><br>";

                tituloEmail += " " + rf.fromConfiguration("application.shorttitle");
                EmailSender emailSender = new EmailSender.Builder()
                        .header(tituloEmail)
                        .messages(mensajeEmail)
                        .pathFile("")
                        .nameFile("")
                        .emailList(emailColaboradorList)
                        .build();

                emailSenderEvent.fire(new EmailSenderEvent(emailSender));
                // FacesUtil.successMessage(rf.fromMessage("info.emailexitoso"));
            }
            if (emailPropietarioList == null || emailPropietarioList.isEmpty()) {

            } else {

                tituloEmail = rf.fromMessage("emailtitulo.proyectocreado");

                mensajeEmail
                        = "<strong>" + rf.fromMessage("email.proyecto") + "</strong>" + " " + proyecto.getProyecto() + " <br>"
                        + "<strong>" + rf.fromMessage("email.enviadopor") + "</strong>" + " " + userLogged.getName() + "<br>"
                        + "<strong>" + rf.fromMessage("email.evento") + "</strong>" + " " + rf.fromMessage("email.asignadoaproyectocomopropietario") + "<br><br>";

                tituloEmail += " " + rf.fromConfiguration("application.shorttitle");

                EmailSender emailSender = new EmailSender.Builder()
                        .header(tituloEmail)
                        .messages(mensajeEmail)
                        .pathFile("")
                        .nameFile("")
                        .emailList(emailPropietarioList)
                        .build();

                emailSenderEvent.fire(new EmailSenderEvent(emailSender));
                // FacesUtil.successMessage(rf.fromMessage("info.emailexitoso"));
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }
// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="public String refreshFromWebsocket()">
    @Override
    public String refreshFromWebsocket() {
        try {
            /**
             * Leer los datos del Websocket
             */
            String event = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("event");

            String channel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("channel");

            String message = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("message");

            MessageWebSocket mws = new MessageWebSocket();
            mws = mws.toMessageWebSocket(message);

            if (jmoordbCronometerOld.equals(0L)) {

                if (!dialogVisibleWebSocket) {
                    refresh();
                    PrimeFaces.current().ajax().update("form");
                }
                jmoordbCronometerOld = JmoordbCronometer.nanoSecondsNow();
            } else {

                Long dif = JmoordbCronometer.diference(jmoordbCronometerOld, JmoordbCronometer.nanoSecondsNow());
                Long seconds = dif / 1000;
                contadorNotificacionesWebsocket++;
                if (seconds < websocketMinimumsSecondsForUpdate.get().longValue()) {

                } else {
                    if (!dialogVisibleWebSocket) {
                        contadorNotificacionesWebsocket = 0;
                        refresh();
                        PrimeFaces.current().ajax().update("form");
                    }
                }

                jmoordbCronometerOld = JmoordbCronometer.nanoSecondsNow();
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String updateForRemoteWebsocket()">

    @Override
    public String updateForRemoteWebsocket() {
        try {
            if (dialogVisibleAddWebSocket) {
                closeOverlayPanel("PF('overlayPanelProyectoAgregar').show()");
            }
            if (!dialogVisibleWebSocket) {
                return ":form";
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="void onCompleteWebSocket()">
    public void onCompleteWebSocket() {
        try {

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>
    

   

    @Override
    public void sendMensajesWebsocket(MessageWebSocket messageWebSocket, Boolean exclude) {
        try {
            List<UserView> result = filterUserViewListForWebSocket(userViewForWebSocketList, exclude, userLogged);

            webSocketController.sendMessageProyectoMultiple(messageWebSocket.toJSON(), result);

            webSocketController.sendMessageTableroMultiple(messageWebSocket.toJSON(), result);
            webSocketController.sendMessageSprintMultiple(messageWebSocket.toJSON(), result);
            webSocketController.sendMessagePapeleraReciclajeMultiple(messageWebSocket.toJSON(), result);
            webSocketController.sendMessageBacklogMultiple(messageWebSocket.toJSON(), result);
            webSocketController.sendMessagePanelTrabajoMultiple(messageWebSocket.toJSON(), result);
           

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Boolean validDateProject(Proyecto proyecto)">
    public Boolean validDateProject(Proyecto proyecto) {
        Boolean result = Boolean.TRUE;
        try {
            String resultV = validarEstadoIniciadoYFechaProyecto(proyecto, rf);
            if (!resultV.equals("")) {
                result = Boolean.FALSE;
            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="void handleCloseDialogRefresh(CloseEvent event)">
    @Override
    public void handleCloseDialogRefresh(CloseEvent event) {
        refresh();
    }
    // </editor-fold>  

  
    // <editor-fold defaultstate="collapsed" desc="String visualizarDetallesProyecto()">

    public String visualizarDetallesProyecto() {
        try {

        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="setFirstPageDataTable()">
    public void setFirstPageDataTable() {

        if (dataTable != null) {
            dataTable.setFirst(0);
        }
    }
    // </editor-fold>


}
