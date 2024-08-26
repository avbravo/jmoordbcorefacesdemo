/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.faces;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbCoreContext;
import com.avbravo.jmoordbutils.JmoordbCoreDateUtil;
import com.avbravo.jmoordbutils.JmoordbCoreXHTMLUtil;
import com.avbravo.jmoordbutils.JmoordbCronometer;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.ReportUtils;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaContext;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaManager;
import com.avbravo.jmoordbutils.paginator.IPaginator;
import com.avbravo.jmoordbutils.paginator.Paginator;
import com.avbravo.jmoordbutils.websocket.MessageWebSocket;
import com.jmoordb.core.model.Pagination;
import com.jmoordb.core.model.Sorted;
import com.jmoordb.core.util.JmoordbCoreUtil;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import com.nerys.converter.services.ClienteConverterServices;
import com.nerys.faces.services.ClienteFacesServices;
import com.nerys.faces.services.FacesServices;
import com.nerys.model.ActionHistory;
import com.nerys.model.Cliente;
import com.nerys.model.Profile;
import com.nerys.model.Proyecto;
import com.nerys.model.Representante;
import com.nerys.model.Sede;
import com.nerys.model.User;
import com.nerys.model.UserView;

import com.nerys.restclient.ProyectoRestClient;
import com.nerys.restclient.UserRestClient;
import com.nerys.services.ClienteServices;
import com.nerys.services.ColorManagement;
import com.nerys.services.ProyectoServices;
import com.nerys.services.RepresentanteServices;
import com.nerys.services.SedeServices;
import com.nerys.services.implementation.ColorManagementImpl;
import com.nerys.websocket.fire.FacesWebSocket;
import com.nerys.websocket.fire.WebSocketController;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.ResponsiveOption;
import org.primefaces.model.SortMeta;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Optional;
import jakarta.annotation.PreDestroy;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
@Data
public class ClienteFaces implements Serializable, JmoordbCoreXHTMLUtil, IPaginator, ClienteFacesServices, FacesServices, FacesWebSocket {

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
    ClienteConverterServices clienteConverterServices;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" fields">
    private static final long serialVersionUID = 1L;

    private User userLogged = new User();
    private String message = "";
    private String tituloDialogo = "";
    private Boolean isOverlayPanelOpen = Boolean.FALSE;
    private Boolean isButtonSavePressed = Boolean.TRUE;
    private Boolean fueCambiadoPorOtroUsuario = Boolean.FALSE;
    private Boolean isJuridico = Boolean.FALSE;
    private Boolean buttonNewPressed = Boolean.TRUE;
    private Boolean showDialogContent = Boolean.TRUE;
    private String mascaraCedulaRuc = "99-9999-99999";
    private String ced_ = "";
    String text = "";

    private List<SelectItem> formatos;
    private String selection;

    Integer indexRow = 0;
    // Invocados desde otro fomulario
    private Boolean isEditable = Boolean.TRUE;
    private String callerLevel0 = "";
    private Profile profileLogged = new Profile();

    private Cliente clienteSelectedAutocomplete = new Cliente();
    private Cliente clienteSelected = new Cliente();
    private Cliente clienteSelectedCopy = new Cliente();
    private Cliente clienteDB = new Cliente();
    private Proyecto proyectoSelected = new Proyecto();

    private List<ResponsiveOption> responsiveOptions;

    private List<Cliente> clienteList = new ArrayList<>();
    private List<Sede> sedes = new ArrayList<>();
    private Sede sedeSelected = new Sede();
    private List<Representante> representantes = new ArrayList<>();
    private Representante representanteSelected = new Representante();

    private Boolean haveClienteOpen = Boolean.FALSE;
    private Boolean isRowPageSmall = Boolean.TRUE;

    ColorManagement colorKnob = new ColorManagementImpl();
    private DataTable dataTable;
    Integer totalRecords = 0;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="selected For Dialog()">
    public Cliente getclienteSelected() {

        if (clienteSelected == null) {
            clienteSelected = new Cliente();
        }
        return clienteSelected;
    }

    public Proyecto getProyectoSelected() {
        if (proyectoSelected == null) {
            proyectoSelected = new Proyecto();
        }
        return proyectoSelected;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="LazyDataModel">
    private LazyDataModel<Cliente> clienteLazyDataModel;

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="paginator ">
    Paginator paginator = new Paginator();
    Paginator paginatorOld = new Paginator();

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="@Inject">
    @Inject
    JmoordbResourcesFiles rf;
    @Inject
    JmoordbCoreMediaManager jmoordbCoreMediaManager;
    @Inject
    JmoordbCoreMediaContext jmoordbCoreMediaContext;

// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="Services">
    @Inject
    ProyectoServices proyectoServices;
    @Inject
    RepresentanteServices representanteServices;
    @Inject
    ClienteServices clienteServices;
    @Inject
    RepresentanteServices representateServices;
    @Inject
    SedeServices sedeServices;

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
    @ConfigProperty(name = "converter.max.number.of.elements")
    private Provider<Integer> converterMaxNumberOfElements;
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

    @Inject
    @ConfigProperty(name = "pathBaseLinuxAddUserHomeStoreInCollections", defaultValue = "false")
    private Provider<Boolean> pathBaseLinuxAddUserHomeStoreInCollections;

    @Inject
    @ConfigProperty(name = "formato.cedulanatural")
    private Provider<String> formatoCedulanatural;

    @Inject
    @ConfigProperty(name = "formato.panamenoextranjero")
    private Provider<String> formatoPanamenoextranjero;
    @Inject
    @ConfigProperty(name = "formato.extranjeroconcedula")
    private Provider<String> formatoExtranjeroconcedula;
    @Inject
    @ConfigProperty(name = "formato.naturalizado")
    private Provider<String> formatoNaturalizado;
    @Inject
    @ConfigProperty(name = "formato.panamenoantesvigencia")
    private Provider<String> formatoPanamenoantesvigencia;
    @Inject
    @ConfigProperty(name = "formato.poblacionindigena")
    private Provider<String> formatoPoblacionindigena;
    @Inject
    @ConfigProperty(name = "formato.numeropasaporte")
    private Provider<String> formatoNumeropasaporte;
    @Inject
    @ConfigProperty(name = "formato.juridico")
    private Provider<String> formatoJuridico;
    // #Websocket    
    @Inject
    @ConfigProperty(name = "websocket.minimums.seconds.for.update")
    private Provider<Integer> websocketMinimumsSecondsForUpdate;
    // </editor-fold>
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

            /*
            WebSocket
             */
            contadorNotificacionesWebsocket = 0;
            jmoordbCronometerOld = 0L;
            dialogVisibleWebSocket = Boolean.FALSE;
            dialogVisibleAddWebSocket = Boolean.FALSE;

            haveClienteOpen = Boolean.FALSE;
            message = "";
            selection = rf.fromMessage("selectonemenu.natural");
            userLogged = (User) JmoordbCoreContext.get("LoginFaces.userLogged");
            profileLogged = (Profile) JmoordbCoreContext.get("LoginFaces.profileLogged");
            clienteSelectedAutocomplete = new Cliente();
            saveToMediaContext(userLogged.getPhoto());
            responsiveOptions = new ArrayList<>();
            responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
            responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
            responsiveOptions.add(new ResponsiveOption("560px", 1, 1));

//            formatos = new ArrayList<>();
//            SelectItemGroup groupNatural = new SelectItemGroup(rf.fromMessage("selectonemenu.natural"));
//            groupNatural.setValue(rf.fromMessage("selectonemenu.natural"));
//
//            SelectItem optionNatural = new SelectItem(rf.fromMessage("selectonemenu.natural"));
//            SelectItem optionPanamenoextranjero = new SelectItem(rf.fromMessage("selectonemenu.panamenoextranjero"));
//            SelectItem optionExtranjeroconcedula = new SelectItem(rf.fromMessage("selectonemenu.extranjeroconcedula"));
//            SelectItem optionNaturalizado = new SelectItem(rf.fromMessage("selectonemenu.naturalizado"));
//            SelectItem optionPanamenoantesvigencia = new SelectItem(rf.fromMessage("selectonemenu.panamenoantesvigencia"));
//            SelectItem optionPoblacionindigena = new SelectItem(rf.fromMessage("selectonemenu.poblacionindigena"));
//            SelectItem optionNumeropasaporte = new SelectItem(rf.fromMessage("selectonemenu.numeropasaporte"));
//            groupNatural.setSelectItems(new SelectItem[]{optionNatural, optionPanamenoextranjero, optionExtranjeroconcedula,
//                optionNaturalizado, optionPanamenoantesvigencia, optionPoblacionindigena, optionNumeropasaporte});
//
//            SelectItemGroup groupJuridico = new SelectItemGroup(rf.fromMessage("selectonemenu.juridico"));
//            groupJuridico.setValue(rf.fromMessage("selectonemenu.juridico"));
//
//             SelectItem optionJuridico = new SelectItem(rf.fromMessage("selectonemenu.juridico"));
//            groupJuridico.setSelectItems(new SelectItem[]{optionJuridico});
//             
//             
//            formatos.add(groupNatural);
//
//            formatos.add(groupJuridico);
            findAll();

            this.clienteLazyDataModel = new LazyDataModel<Cliente>() {
                @Override
                public List<Cliente> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                    switch (paginator.getName()) {
                        case "findAll":

                            totalRecords = clienteServices.count(paginator.getFilter(),
                                    paginator.getSort(), 0, 0).intValue();
                            break;

                    }

                    List<Paginator> list = new ArrayList<>();
                    if (!isRowPageSmall) {

                        /**
                         * Utiliza rowPage
                         */
                        list = processLazyDataModel(paginator, paginatorOld, offset, rowPage.get(), totalRecords, sortBy);

                    } else {

                        /**
                         * Utiliza rowPageWithOverlayPanel para el OverlayPanel
                         */
                        list = processLazyDataModel(paginator, paginatorOld, offset, rowPageSmall.get(), totalRecords, sortBy);

                    }
//

                    paginator = list.get(0);
                    paginatorOld = list.get(1);
                    Pagination pagination = new Pagination();
                    if (!isRowPageSmall) {

                        paginator.setNumberOfPage(numberOfPages(totalRecords, rowPage.get()));
                        pagination = new Pagination(paginator.getPage(), rowPage.get());
                    } else {

                        paginator.setNumberOfPage(numberOfPages(totalRecords, rowPageSmall.get()));
                        pagination = new Pagination(paginator.getPage(), rowPageSmall.get());
                    }

                    List<Cliente> result = new ArrayList<>();
                    switch ((paginator.getName())) {
                        case "findAll":

                            result = clienteServices.lookup(paginator.getFilter(),
                                    paginator.getSort(), paginator.getPage(), rowPageSmall.get());

                            break;
                        default:

                    }

                    clienteLazyDataModel.setRowCount(totalRecords);

                    PrimeFaces.current().executeScript("setDataTableWithPageStart()");
                    PrimeFaces.current().executeScript("widgetVardataTable.getPaginator().setPage(0);");
                    clienteList = result;

                    return result;
                }

                @Override
                public int count(Map<String, FilterMeta> map) {

                    return totalRecords;

                }

                @Override
                public String getRowKey(Cliente object) {
                    if (object == null || object.getIdcliente() == null) {
                        return "";
                    }
                    return object.getIdcliente().toString();
                }

                @Override
                public Cliente getRowData(String rowKey) {
                    for (Cliente t : clienteList) {
                        if (t != null) {
                            if (t.getIdcliente().equals(rowKey)) {
                                return t;
                            }
                        }
                    }
                    return null;
                }
            };

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void preDestroy()">
    @PreDestroy
    @Override
    public void preDestroy() {
        clienteConverterServices.destroyed();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String findAll()">
    public String findAll() {
        try {

            Bson filter = ne("cliente", "");

            Document sort = new Document("cliente", 1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("idcliente", -1)))
                            .title("Todos")
                            .name("findAll")
                            .build();

            /**
             * Limpiar los elementos
             */
            setFirstPageDataTable();
        } catch (Exception e) {
            // FacesUtil.errorMessage(FacesUtil.nameOfMethod() + "() : " + e.getLocalizedMessage());
        }

        return "";
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String findByIdCliente()">

    public String findByIdCliente() {
        try {

            Bson filter = eq("idcliente", clienteSelectedAutocomplete.getIdcliente());
            Document sort = new Document("name", 1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("name", -1)))
                            .title("Todos")
                            .name("findAll")
                            .build();

            /**
             * Limpiar los elementos
             */
            setFirstPageDataTable();
        } catch (Exception e) {
            // FacesUtil.errorMessage(FacesUtil.nameOfMethod() + "() : " + e.getLocalizedMessage());
        }

        return "";
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
    // <editor-fold defaultstate="collapsed" desc="setFirstPageDataTable()">
    public void setFirstPageDataTable() {

        if (dataTable != null) {
            dataTable.setFirst(0);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="save(Cliente cliente)">
    public void save(Cliente cliente) {
        try {

            Optional<Cliente> keyChange = clienteServices.findByCedularuc(cliente.getCedularuc());
            if (!keyChange.isEmpty()) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.clienteconigualcedularuc"));
                return;
            }

            Map.Entry<Boolean, String> validCliente = clienteServices.isValid(cliente);

            if (!validCliente.getKey()) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(validCliente.getValue()));
                return;
            }
            clienteSelected = cliente;
            clienteSelected.setName(clienteSelected.getName().trim());

            if (cliente.getName() == null || cliente.getName().equals("")) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.ingresecliente"));
                return;
            }

            if (clienteSelected.getTipocliente().equals(rf.fromMessage("selectonemenu.juridico"))) {
                /**
                 * SEDE
                 */
                if (sedes == null || sedes.isEmpty()) {
                    FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.ingresesede"));
                    return;
                } else {

                    for (Sede sede : sedes) {
                        Map.Entry<Boolean, String> valid = sedeServices.isValid(sede);

                        if (!valid.getKey()) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(valid.getValue()));
                            return;
                        }

                        Boolean found = Boolean.FALSE;
                        for (Sede s : sedes) {
                            if (s.getNombre().equals(sedeSelected.getNombre()) || s.getIdsede().equals(sedeSelected.getIdsede())) {

                                found = Boolean.TRUE;
                                break;

                            }
                        }
                        if (found) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.sederepetida"));
                            return;
                        }
                    }
                }

                /**
                 * Representante
                 */
                if (representantes == null || representantes.isEmpty()) {
                    FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.representante"));
                    return;
                } else {
                    for (Representante representante : representantes) {

                        Map.Entry<Boolean, String> valid = representanteServices.isValid(representante);

                        if (!valid.getKey()) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(valid.getValue()));
                            return;
                        }

                        Boolean found = Boolean.FALSE;
                        for (Representante r : representantes) {
                            if (r.getCedula().equals(representanteSelected.getCedula())) {

                                found = Boolean.TRUE;

                            }
                        }
                        if (found) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.cedularepresentanterepetido"));
                            return;
                        }
                    }
                }
                clienteSelected.setRepresentante(representantes);
            } else {
                sedes = new ArrayList<>();
                representantes = new ArrayList<>();

            }

            clienteSelected.setSede(sedes);

            clienteSelected.setRepresentante(representantes);

            /**
             *
             */
            ActionHistory actionHistory = new ActionHistory.Builder()
                    .iduser(userLogged.getIduser())
                    .fecha(JmoordbCoreDateUtil.fechaHoraActual())
                    .evento("crear")
                    .clase(FacesUtil.nameOfClass())
                    .metodo(FacesUtil.nameOfMethod())
                    .build();
            List<ActionHistory> actionHistoryList = new ArrayList<>();
            actionHistoryList.add(actionHistory);

            clienteSelected.setActionHistory(actionHistoryList);
            clienteSelected.setActive(Boolean.TRUE);

            Optional<Cliente> clienteOptional = clienteServices.save(clienteSelected);
            if (!clienteOptional.isPresent()) {

                FacesUtil.warningDialog(rf.fromCore("warning.save"), rf.fromCore("warning.save"));
            } else {

                clienteSelected = clienteOptional.get();
                JmoordbCoreContext.put("ClienteFaces.clienteSelected", clienteSelected);

                /**
                 * Enviar WebSocket
                 */
                MessageWebSocket messageWebSocket = new MessageWebSocket.Builder()
                        .producer(FacesUtil.nameOfClass())
                        .action(FacesUtil.nameOfMethod())
                        .key("cliente")
                        .value(cliente.getIdcliente() != null ? cliente.getIdcliente().toString() : "")
                        .message("Cliente creado")
                        .iduser(userLogged.getIduser())
                        .date(JmoordbCoreDateUtil.fechaHoraActual())
                        .build();
                sendMensajesWebsocket(messageWebSocket, Boolean.TRUE);

                //  FacesUtil.infoDialog(rf.fromCore("info.save"), rf.fromCore("info.save"));
                FacesUtil.successMessage(rf.fromCore("info.save"));

                /**
                 * Actualiza las tarjetas
                 */
                PrimeFaces.current().ajax().update("dataTable");

                closeOverlayPanel("PF('overlayPanelCliente').hide()");
                refresh();
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="edit(Cliente cliente)">

    public void edit(Cliente cliente) {
        try {

            Map.Entry<Boolean, String> validCliente = clienteServices.isValid(cliente);

            if (!validCliente.getKey()) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(validCliente.getValue()));
                return;
            }

            Cliente clienteDBNow = clienteServices.findByIdcliente(cliente.getIdcliente()).get();
            if (!clienteDBNow.equals(clienteDB)) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.otrousuarioactualizodocumentosincronicesusdatosprimero"));
                return;
            }
            Optional<Cliente> keyChange = clienteServices.findByCedularuc(cliente.getCedularuc());
            if (!keyChange.isEmpty()) {
                if (!clienteDB.getIdcliente().equals(cliente.getIdcliente())) {
                    FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.clienteconigualcedularuc"));
                    return;
                }
            }

            clienteSelected = cliente;
            clienteSelected.setName(clienteSelected.getName().trim());
            if (clienteSelected.getTipocliente().equals(rf.fromMessage("selectonemenu.juridico"))) {
                /**
                 * SEDE
                 */
                if (sedes == null || sedes.isEmpty()) {
FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.ingresecliente"));
                return;
                } else {

                    for (Sede sede : sedes) {
                        Map.Entry<Boolean, String> valid = sedeServices.isValid(sede);

                        if (!valid.getKey()) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(valid.getValue()));
                            return;
                        }

                        Boolean found = Boolean.FALSE;
                        for (Sede s : sedes) {
                            if (s.getNombre().equals(sedeSelected.getNombre()) || s.getIdsede().equals(sedeSelected.getIdsede())) {

                                found = Boolean.TRUE;
                                break;

                            }
                        }
                        if (found) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.sederepetida"));
                            return;
                        }
                    }
                }

                /**
                 * Representante
                 */
                if (representantes == null || representantes.isEmpty()) {
 FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.representante"));
                    return;
                } else {
                    for (Representante representante : representantes) {

                        Map.Entry<Boolean, String> valid = representanteServices.isValid(representante);

                        if (!valid.getKey()) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(valid.getValue()));
                            return;
                        }

                        Boolean found = Boolean.FALSE;
                        for (Representante r : representantes) {
                            if (r.getCedula().equals(representanteSelected.getCedula())) {

                                found = Boolean.TRUE;

                            }
                        }
                        if (found) {
                            FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.cedularepresentanterepetido"));
                            return;
                        }
                    }
                }
                clienteSelected.setRepresentante(representantes);
            } else {
                sedes = new ArrayList<>();
                representantes = new ArrayList<>();

            }

            clienteSelected.setSede(sedes);

            clienteSelected.setRepresentante(representantes);
            /**
             *
             */
            ActionHistory actionHistory = new ActionHistory.Builder()
                    .iduser(userLogged.getIduser())
                    .fecha(JmoordbCoreDateUtil.fechaHoraActual())
                    .evento("editar")
                    .clase(FacesUtil.nameOfClass())
                    .metodo(FacesUtil.nameOfMethod())
                    .build();
            List<ActionHistory> actionHistoryList = new ArrayList<>();

            actionHistoryList.addAll(clienteSelected.getActionHistory());

            actionHistoryList.add(actionHistory);

            clienteSelected.setActionHistory(actionHistoryList);

            Boolean isUpdate = clienteServices.update(clienteSelected);
            if (!isUpdate) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromCore("warning.update"));
            } else {

                /**
                 * Enviar WebSocket
                 */
                MessageWebSocket messageWebSocket = new MessageWebSocket.Builder()
                        .producer(FacesUtil.nameOfClass())
                        .action(FacesUtil.nameOfMethod())
                        .key("cliente")
                        .value(cliente.getIdcliente() != null ? cliente.getIdcliente().toString() : "")
                        .message("Cliente editado")
                        .iduser(userLogged.getIduser())
                        .date(JmoordbCoreDateUtil.fechaHoraActual())
                        .build();
                sendMensajesWebsocket(messageWebSocket, Boolean.TRUE);
                FacesUtil.successMessage(rf.fromCore("info.update"));

                /**
                 * Actualiza las tarjetas
                 */
                //  clienteList.add(clienteSelected);
                //  PrimeFaces.current().ajax().update("dataTable");
                closeOverlayPanel("PF('overlayPanelCliente').hide()");
                // refresh();
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void closeOverlayPanel(String panel) ">
    private void closeOverlayPanel(String panel) {
        try {
            PrimeFaces.current().executeScript(panel);
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void agregarRowCliente()">
    public void agregarRowCliente() {
        try {
            dialogVisibleWebSocket = Boolean.TRUE;
            dialogVisibleAddWebSocket = Boolean.TRUE;

            buttonNewPressed = Boolean.TRUE;
            showDialogContent = Boolean.FALSE;
            tituloDialogo = rf.fromMessage("dialog.clienteragregar");
            showDialogContent = Boolean.TRUE;
            isJuridico = Boolean.FALSE;
            clienteSelected = new Cliente();
            clienteSelected.setActive(Boolean.TRUE);
            sedes = new ArrayList<>();
            representantes = new ArrayList<>();
            isButtonSavePressed = Boolean.FALSE;

            PrimeFaces.current().ajax().update("outputPanelCliente");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String refresh()">
    @Override
    public String refresh() {
        try {
            contadorNotificacionesWebsocket = 0;
            dialogVisibleWebSocket = Boolean.FALSE;
            dialogVisibleAddWebSocket = Boolean.FALSE;

            clienteList = new ArrayList<>();
            findAll();
            PrimeFaces.current().ajax().update("form");
            PrimeFaces.current().ajax().update("dataTable");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String prepareCliente(Cliente cliente)">
    public String prepareCliente(Cliente cliente) {
        try {
            dialogVisibleWebSocket = Boolean.TRUE;
            clienteSelected = new Cliente();
            showDialogContent = Boolean.TRUE;
            sedes = cliente.getSede();
//            sedes.add(new Sede("", "", "", "", "", Boolean.TRUE));
            representantes = cliente.getRepresentante();
            clienteSelected = validarSiCambioCliente(cliente);
            clienteSelectedCopy = new Cliente();
            JmoordbCoreUtil.copyBeans(clienteSelectedCopy, clienteSelected);

            isOverlayPanelOpen = Boolean.TRUE;
            isButtonSavePressed = Boolean.TRUE;
            buttonNewPressed = Boolean.FALSE;
            tituloDialogo = rf.fromMessage("dialog.clienteeditar");

            mascaraCedulaRuc = generateFormatoCedulaRuc(clienteSelected.getTipocliente());

//PrimeFaces.current().ajax().update("overlayPanelCliente");
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Cliente validarSiCambioCliente(Cliente cliente)">
    private Cliente validarSiCambioCliente(Cliente cliente) {
        Cliente result = new Cliente();
        try {
            message = "";
            fueCambiadoPorOtroUsuario = Boolean.FALSE;
            this.clienteDB = clienteServices.findByIdcliente(cliente.getIdcliente()).get();
            if (clienteDB.equals(cliente)) {
                result = cliente;
            } else {
                fueCambiadoPorOtroUsuario = Boolean.TRUE;
                message = rf.fromMessage("warning.sincroniceformularioregistroactualizadoporotrousuario");
                result = clienteDB;
                showDialogContent = Boolean.FALSE;

            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String printAll()">
    public String printAll() {

//        com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.A4);
//        com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.LETTER);
        com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.LETTER.rotate());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, baos);
            // METADATA

            document.open();
            document.add(
                    ReportUtils.paragraph(
                            rf.fromMessage("print.titlecliente"), FontFactory.getFont("arial", 12, Font.BOLD), Element.ALIGN_CENTER));

            Date currentDate = new Date();

            /**
             * Si se desea imprimir el filtro
             */
//            String textoFiltro = paginator.getTitle();
//            document.add(
//                    ReportUtils.paragraph(
//                            textoFiltro, FontFactory.getFont("arial", 10, Font.BOLD), Element.ALIGN_CENTER));
            /**
             *
             */
            String date = showDate(currentDate) + " " + showHour(currentDate);

            document.add(
                    ReportUtils.paragraph(
                            rf.fromMessage("print.fecha") + " " + date, FontFactory.getFont("arial", 8, Font.BOLD), Element.ALIGN_RIGHT));
            document.add(new Paragraph("\n"));

            // Numero de columnas
            PdfPTable table = new PdfPTable(8);

            // Aqui indicamos el tamaño de cada columna
            table.setTotalWidth(new float[]{60, 100, 120, 60, 100, 120, 120, 60});

            table.setLockedWidth(true);

            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.idcliente"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.cedularuc"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.cliente"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.telefono"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.direccion"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.sede"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.representante"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));

            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.active"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));

//            
            //Invoca todos los registros con paginacion para imprimir
            Integer pageActual = 1;
            Integer totalPage = paginator.getNumberOfPage();
            //Carga los registros por paginacion

            for (int i = 1; i <= totalPage; i++) {

                pageActual = i;

                List<Cliente> list = clienteServices.lookup(paginator.getFilter(),
                        paginator.getSort(), pageActual, rowPageSmall.get());

                if (list == null || list.isEmpty()) {

                } else {

                    list.forEach(c -> {

                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getIdcliente().toString(), FontFactory.getFont("arial", 10, Font.NORMAL)));

                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getCedularuc(), FontFactory.getFont("arial", 9, Font.NORMAL)));
                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getName(), FontFactory.getFont("arial", 9, Font.NORMAL)));
                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getTelefono(), FontFactory.getFont("arial", 9, Font.NORMAL)));
                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getDireccion(), FontFactory.getFont("arial", 9, Font.NORMAL)));

                        text = "";
                        indexRow = 1;
                        if (c.getSede() == null || c.getSede().isEmpty()) {
                        } else {

                            for (Sede s : c.getSede()) {
                                if (indexRow.equals(c.getSede().size())) {
                                    text += s.getNombre() + " ";
                                } else {
                                    text += s.getNombre() + ",";
                                }
                                indexRow++;
                            }
                        }

                        table.addCell(
                                ReportUtils.PdfCell(
                                        text, FontFactory.getFont("arial", 9, Font.NORMAL)));

                        text = "";
                        indexRow = 1;
                        if (c.getRepresentante() == null || c.getRepresentante().isEmpty()) {
                        } else {

                            for (Representante r : c.getRepresentante()) {
                                if (indexRow.equals(c.getRepresentante().size())) {
                                    text += r.getNombre() + " ";
                                } else {
                                    text += r.getNombre() + ",";
                                }
                                indexRow++;
                            }
                        }

                        table.addCell(
                                ReportUtils.PdfCell(
                                        text, FontFactory.getFont("arial", 9, Font.NORMAL)));

                        String activo = "";
                        if (c.getActive()) {
                            activo = "si";
                        } else {
                            activo = "no";
                        }

                        table.addCell(
                                ReportUtils.PdfCell(activo, FontFactory.getFont("arial", 10, Font.NORMAL)));

                    });
                }
            }

            //Reestablece la pagina actual
            //paginator.setPage(pageActual);
            document.add(table);
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        document.close();

        ReportUtils.printPDF(baos);
        return "";
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Cliente> completeCliente(String query)">
    public List<Cliente> completeCliente(String query) {

        List<Cliente> result = new ArrayList<>();
        try {
            query = query.trim();
            result = clienteServices.likeByName(query);
            clienteConverterServices.add(result.subList(0,
                    calcularConverterMaxNumberOfElements(result.size(), converterMaxNumberOfElements.get()))
            );
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="List<Cliente> completeClienteCedulaRuc(String query)">

    public List<Cliente> completeCedulaRuc(String query) {

        List<Cliente> result = new ArrayList<>();
        try {
            query = query.trim();
            result = clienteServices.likeByCedularuc(query);
            clienteConverterServices.add(result.subList(0,
                    calcularConverterMaxNumberOfElements(result.size(), converterMaxNumberOfElements.get()))
            );
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="  autocompleteSelectedEvent(SelectEvent<Cliente> event)">
    public void autocompleteSelectedEvent(SelectEvent<Cliente> event) {
        try {

            findByIdCliente();
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void autocompleteUnselectListener(UnselectEvent event)">
    public void autocompleteUnselectListener(UnselectEvent event) {

    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="void handleCloseDialogRefresh(CloseEvent event)">

    @Override
    public void handleCloseDialogRefresh(CloseEvent event) {
        refresh();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void deleteSede(Sede sede)">
    public void deleteSede(Sede sede) {
        try {
            sedes.remove(sede);
            isButtonSavePressed = Boolean.FALSE;

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="void deleteRepresentante(Representante representante)">
    public void deleteRepresentante(Representante representante) {
        try {
            representantes.remove(representante);
            isButtonSavePressed = Boolean.FALSE;

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void agregarRowSede(String dataTable)">
    public void agregarRowSede(String dataTable) {
        try {
            if (!buttonNewPressed) {
                if (clienteSelected == null || clienteSelected.getIdcliente() == null) {

                    clienteSelected = new Cliente();
                    JmoordbCoreUtil.copyBeans(clienteSelected, clienteSelectedCopy);
                }
            }

            sedes.add(new Sede(JmoordbCoreUtil.generateUniqueID(), "", "", "", "", Boolean.TRUE));
            isButtonSavePressed = Boolean.FALSE;

            PrimeFaces.current().ajax().update(dataTable);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="void agregarRowRepresentante(String dataTable)">
    public void agregarRowRepresentante(String dataTable) {
        try {
            if (!buttonNewPressed) {
                if (clienteSelected == null || clienteSelected.getIdcliente() == null) {

                    clienteSelected = new Cliente();
                    JmoordbCoreUtil.copyBeans(clienteSelected, clienteSelectedCopy);
                }
            }

            representantes.add(new Representante(0L, "", "", "", "", "", Boolean.TRUE));

            isButtonSavePressed = Boolean.FALSE;

            PrimeFaces.current().ajax().update(dataTable);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }

    // </editor-fold>
    public void onRowEdit(RowEditEvent<Sede> event) {
        FacesMessage msg = new FacesMessage("Product Edited", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Sede> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        try {
            if (!buttonNewPressed) {
                if (clienteSelected == null || clienteSelected.getIdcliente() == null) {

                    clienteSelected = new Cliente();
                    JmoordbCoreUtil.copyBeans(clienteSelected, clienteSelectedCopy);
                }
            }

            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }

    public void onRowEditRepresentante(RowEditEvent<Representante> event) {
        FacesMessage msg = new FacesMessage("Product Edited", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancelRepresentante(RowEditEvent<Sede> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getNombre()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEditRepresentante(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onItemSelectedListener(SelectEvent event) {
        try {

            String selectedItem = (String) event.getObject();
            if (selectedItem.equals(rf.fromMessage("selectonemenu.juridico"))) {
                isJuridico = Boolean.TRUE;
            } else {
                isJuridico = Boolean.FALSE;
            }
            mascaraCedulaRuc = generateFormatoCedulaRuc(selectedItem);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }
// <editor-fold defaultstate="collapsed" desc="String validFormato(String tipo)">

    public String generateFormatoCedulaRuc(String tipocliente) {
        var result = "";
        try {
            if (tipocliente.equals(rf.fromMessage("selectonemenu.juridico"))) {

                result = formatoJuridico.get();
            } else {

                if (tipocliente.equals(rf.fromMessage("selectonemenu.natural"))) {
                    result = formatoCedulanatural.get();
                } else {
                    if (tipocliente.equals(rf.fromMessage("selectonemenu.panamenoextranjero"))) {
                        result = formatoPanamenoextranjero.get();
                    } else {
                        if (tipocliente.equals(rf.fromMessage("selectonemenu.extranjeroconcedula"))) {
                            result = formatoExtranjeroconcedula.get();
                        } else {
                            if (tipocliente.equals(rf.fromMessage("selectonemenu.naturalizado"))) {
                                result = formatoNaturalizado.get();
                            } else {
                                if (tipocliente.equals(rf.fromMessage("selectonemenu.panamenoantesvigencia"))) {
                                    result = formatoPanamenoantesvigencia.get();
                                } else {
                                    if (tipocliente.equals(rf.fromMessage("selectonemenu.poblacionindigena"))) {
                                        result = formatoPoblacionindigena.get();
                                    } else {
                                        if (tipocliente.equals(rf.fromMessage("selectonemenu.numeropasaporte"))) {
                                            result = formatoNumeropasaporte.get();
                                        } else {
                                            result = formatoCedulanatural.get();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return result;
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
                    //Valida los datos del WebSocket
                    if (mws.getProducer().equals("DashboardFaces") && mws.getKey().equals("proyecto")
                            && mws.getValue().equals(proyectoSelected.getIdproyecto().toString())) {
                        //    validateChangeInProject();
                    }

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
                        //Valida los datos del WebSocket
                        if (mws.getProducer().equals("DashboardFaces") && mws.getKey().equals("proyecto")
                                && mws.getValue().equals(proyectoSelected.getIdproyecto().toString())) {
                            //    validateChangeInProject();
                        }
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
                closeOverlayPanel("PF('overlayPanelSprint').show()");
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
  
    // <editor-fold defaultstate="collapsed" desc="sendMensajesWebsocket(MessageWebSocket messageWebSocket, Boolean exclude)">

    @Override
    public void sendMensajesWebsocket(MessageWebSocket messageWebSocket, Boolean exclude) {
        try {
            List<UserView> result = filterUserViewListForWebSocket(userViewForWebSocketList, exclude, userLogged);

            webSocketController.sendMessageClienteMultiple(messageWebSocket.toJSON(), result);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
// </editor-fold>

    public void onCompleteWebSocket() {
        try {

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }

}
