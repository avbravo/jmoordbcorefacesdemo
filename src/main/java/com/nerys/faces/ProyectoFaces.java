/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nerys.faces;

import com.avbravo.jmoordbutils.FacesUtil;
import com.avbravo.jmoordbutils.JmoordbCoreContext;
import com.avbravo.jmoordbutils.JmoordbCoreDateUtil;
import com.avbravo.jmoordbutils.JmoordbCoreXHTMLUtil;
import com.avbravo.jmoordbutils.JmoordbResourcesFiles;
import com.avbravo.jmoordbutils.ReportUtils;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaContext;
import com.avbravo.jmoordbutils.media.JmoordbCoreMediaManager;
import com.avbravo.jmoordbutils.paginator.IPaginator;
import com.avbravo.jmoordbutils.paginator.Paginator;
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
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.ne;
import static com.mongodb.client.model.Filters.or;
import com.nerys.converter.services.ClienteConverterServices;

import com.nerys.converter.services.ProyectoConverterServices;
import com.nerys.faces.services.FacesServices;

import com.nerys.model.ActionHistory;
import com.nerys.services.implementation.ColorManagementImpl;
import com.nerys.model.Profile;
import com.nerys.model.Proyecto;

import com.nerys.model.User;
import com.nerys.services.ProyectoServices;
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
import com.nerys.services.ColorManagement;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Optional;
import com.nerys.restclient.UserRestClient;
import com.nerys.restclient.ProyectoRestClient;
import com.nerys.faces.services.ProyectoFacesServices;
import com.nerys.model.Cliente;
import com.nerys.services.ClienteServices;
import jakarta.annotation.PreDestroy;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped
@Data
public class ProyectoFaces implements Serializable, JmoordbCoreXHTMLUtil, IPaginator, ProyectoFacesServices, FacesServices {
// <editor-fold defaultstate="collapsed" desc="ConverterServices">

    @Inject
    ProyectoConverterServices proyectoConverterServices;
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

    private Cliente clienteSelectedAutocomplete = new Cliente();
    private Cliente clienteSelectedAutocompleteFilter = new Cliente();

    private Boolean buttonNewPressed = Boolean.TRUE;
    private Boolean showDialogContent = Boolean.TRUE;

    // Invocados desde otro fomulario
    private Boolean isEditable = Boolean.TRUE;
    private String callerLevel0 = "";
    private Profile profileLogged = new Profile();

    private Proyecto proyectoSelected = new Proyecto();
    private Proyecto proyectoSelectedAutocomplete = new Proyecto();
    private Proyecto proyectoDB = new Proyecto();

    private Cliente clienteSelected = new Cliente();

    private List<ResponsiveOption> responsiveOptions;

    private List<Proyecto> proyectoList = new ArrayList<>();
    private List<Cliente> clienteList = new ArrayList<>();

    private Boolean haveProyectoOpen = Boolean.FALSE;
    private Boolean isRowPageSmall = Boolean.TRUE;

    ColorManagement colorKnob = new ColorManagementImpl();
    private DataTable dataTable;
    Integer totalRecords = 0;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="selected For Dialog()">
    public Proyecto getProyectoSelected() {
        if (proyectoSelected == null) {

            proyectoSelected = new Proyecto();
        }

        return proyectoSelected;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="LazyDataModel">
    private LazyDataModel<Proyecto> proyectoLazyDataModel;
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
    ClienteServices clienteServices;

    @Inject
    ProyectoServices proyectoServices;


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
            haveProyectoOpen = Boolean.FALSE;
            message = "";

            userLogged = (User) JmoordbCoreContext.get("LoginFaces.userLogged");
            profileLogged = (Profile) JmoordbCoreContext.get("LoginFaces.profileLogged");
            proyectoSelectedAutocomplete = new Proyecto();
            saveToMediaContext(userLogged.getPhoto());
            responsiveOptions = new ArrayList<>();
            responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
            responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
            responsiveOptions.add(new ResponsiveOption("560px", 1, 1));

            findAll();

            this.proyectoLazyDataModel = new LazyDataModel<Proyecto>() {
                @Override
                public List<Proyecto> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                    switch (paginator.getName()) {
                        case "findAll":

                            totalRecords = proyectoServices.count(paginator.getFilter(),
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

                    List<Proyecto> result = new ArrayList<>();
                    switch ((paginator.getName())) {
                        case "findAll":

                            result = proyectoServices.lookup(paginator.getFilter(),
                                    paginator.getSort(), paginator.getPage(), rowPageSmall.get());

                            break;
                        default:

                    }

                    proyectoLazyDataModel.setRowCount(totalRecords);

                    PrimeFaces.current().executeScript("setDataTableWithPageStart()");
                    PrimeFaces.current().executeScript("widgetVardataTable.getPaginator().setPage(0);");
                    proyectoList = result;

                    return result;
                }

                @Override
                public int count(Map<String, FilterMeta> map) {

                    return totalRecords;

                }

                @Override
                public String getRowKey(Proyecto object) {
                    if (object == null || object.getIdproyecto() == null) {
                        return "";
                    }
                    return object.getIdproyecto().toString();
                }

                @Override
                public Proyecto getRowData(String rowKey) {
                    for (Proyecto t : proyectoList) {
                        if (t != null) {
                            if (t.getIdproyecto().equals(rowKey)) {
                                return t;
                            }
                        }
                    }
                    return null;
                }

            };
            loadCliente();
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
        clienteConverterServices.destroyed();
        proyectoConverterServices.destroyed();

    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void loadCliente()">
    private void loadCliente() {
        try {
            clienteList = new ArrayList<>();

            Integer page = 0;
            Integer size = 0;
            Bson filter = new Document("active", Boolean.TRUE);

            Document sort = new Document("grupoproyecto", 1);

            clienteList = clienteServices.lookup(
                    filter,
                    sort,
                    page, size);
            clienteConverterServices.add(clienteList);

        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String findAll()">
    public String findAll() {
        try {

            Bson filter = eq("departament.iddepartament", profileLogged.getDepartamentView().getIddepartament());
            Document sort = new Document("id", -1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("id", -1)))
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

    // <editor-fold defaultstate="collapsed" desc="String String findByIdClienteProyecto()">
    public String findByIdClienteProyecto() {
        try {

            Bson filter = and(
                    eq("cliente.idcliente", clienteSelectedAutocompleteFilter.getIdcliente()),
                    eq("departament.iddepartament", profileLogged.getDepartamentView().getIddepartament())
            );

            Document sort = new Document("id", -1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("id", -1)))
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

    // <editor-fold defaultstate="collapsed" desc="String findByIdProyecto()">
    public String findByIdProyecto() {
        try {

            Bson filter0 = or(eq("active", Boolean.FALSE),
                    eq("active", Boolean.TRUE)
            );
            Bson filter = and(filter0, eq("idproyecto", proyectoSelectedAutocomplete.getIdproyecto()));
            Document sort = new Document("proyecto", 1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("proyecto", -1)))
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

    // <editor-fold defaultstate="collapsed" desc="save(Proyecto proyecto)">
    public void save(Proyecto proyecto) {
        try {

            proyectoSelected = proyecto;
            proyectoSelected.setDepartamentView(profileLogged.getDepartamentView());
            proyectoSelected.setActive(Boolean.TRUE);
            proyectoSelected.setProyecto(proyectoSelected.getProyecto().trim());

            Map.Entry<Boolean, String> validProyecto = proyectoServices.isValid(proyecto, rf.fromMessage("selectonemenu.juridico"));

            if (!validProyecto.getKey()) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(validProyecto.getValue()));
                return;
            }

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

            proyectoSelected.setActionHistory(actionHistoryList);

            Optional<Proyecto> proyectoOptional = proyectoServices.save(proyectoSelected);
            if (!proyectoOptional.isPresent()) {
                FacesUtil.warningDialog(rf.fromCore("warning.save"), rf.fromCore("warning.save"));
            } else {
                proyectoSelected = proyectoOptional.get();
                JmoordbCoreContext.put("ProyectoFaces.proyectoSelected", proyectoSelected);

                FacesUtil.successMessage(rf.fromCore("info.save"));

                PrimeFaces.current().ajax().update("dataTable");
                closeOverlayPanel("PF('overlayPanelProyecto').hide()");
                refresh();
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="edit(Proyecto proyecto)">

    public void edit(Proyecto proyecto) {
        try {
            Proyecto proyectoDBNow = proyectoServices.findByIdproyecto(proyecto.getIdproyecto()).get();
            if (!proyectoDBNow.equals(proyectoDB)) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.otrousuarioactualizodocumentosincronicesusdatosprimero"));
                return;
            }
            proyectoSelected = proyecto;
            proyectoSelected.setProyecto(proyectoSelected.getProyecto().trim());
            Map.Entry<Boolean, String> validProyecto = proyectoServices.isValid(proyecto, rf.fromMessage("selectonemenu.juridico"));

            if (!validProyecto.getKey()) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage(validProyecto.getValue()));
                return;
            }

            ActionHistory actionHistory = new ActionHistory.Builder()
                    .iduser(userLogged.getIduser())
                    .fecha(JmoordbCoreDateUtil.fechaHoraActual())
                    .evento("editar")
                    .clase(FacesUtil.nameOfClass())
                    .metodo(FacesUtil.nameOfMethod())
                    .build();
            List<ActionHistory> actionHistoryList = new ArrayList<>();
            actionHistoryList.addAll(proyecto.getActionHistory());
            actionHistoryList.add(actionHistory);

            proyectoSelected.setActionHistory(actionHistoryList);

            Boolean isUpdate = proyectoServices.update(proyectoSelected);
            if (!isUpdate) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromCore("warning.update"));
            } else {

                FacesUtil.successMessage(rf.fromCore("info.update"));

                PrimeFaces.current().ajax().update("dataTable");
                closeOverlayPanel("PF('overlayPanelProyecto').hide()");
                refresh();
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

    // <editor-fold defaultstate="collapsed" desc="void agregarRowProyecto()">
    public void agregarRowProyecto() {
        try {
            buttonNewPressed = Boolean.TRUE;
            showDialogContent = Boolean.FALSE;
            clienteSelected = new Cliente();
            showDialogContent = Boolean.TRUE;
            proyectoSelected = new Proyecto();
            proyectoSelected.setFechaincial(JmoordbCoreDateUtil.insertHoursMinutesSecondsToDate(JmoordbCoreDateUtil.getFechaHoraActual(), 8, 0, 0));

            proyectoSelected.setFechafinal(JmoordbCoreDateUtil.insertHoursMinutesSecondsToDate(JmoordbCoreDateUtil.ultimaFechaAnio(), 23, 59, 0));
            proyectoSelected.setEstado(rf.fromMessage("selectonemenu.iniciado"));
            proyectoSelected.setActive(Boolean.TRUE);
            isButtonSavePressed = Boolean.FALSE;

            PrimeFaces.current().ajax().update("outputPanelProyecto");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String refresh()">
    @Override
    public String refresh() {
        try {

            proyectoList = new ArrayList<>();
            findAll();
            PrimeFaces.current().ajax().update("form");
            PrimeFaces.current().ajax().update("dataTable");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String prepareProyecto(Proyecto proyecto)">
    public String prepareProyecto(Proyecto proyecto) {
        try {
            clienteSelectedAutocompleteFilter = new Cliente();
            proyectoSelectedAutocomplete = new Proyecto();
            
            proyectoSelected = new Proyecto();
            JmoordbCoreUtil.copyBeans(proyectoSelected, proyecto);
            proyectoSelected.setActive(Boolean.TRUE);
            clienteSelectedAutocomplete = proyectoSelected.getCliente();
            showDialogContent = Boolean.TRUE;
            proyectoSelected = validarSiCambioProyecto(proyecto);
            isOverlayPanelOpen = Boolean.TRUE;
            isButtonSavePressed = Boolean.TRUE;
            buttonNewPressed = Boolean.FALSE;

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Proyecto validarSiCambioProyecto(Proyecto proyecto)">
    private Proyecto validarSiCambioProyecto(Proyecto proyecto) {
        Proyecto result = new Proyecto();
        try {
            message = "";
            fueCambiadoPorOtroUsuario = Boolean.FALSE;
            this.proyectoDB = proyectoServices.findByIdproyecto(proyecto.getIdproyecto()).get();
            if (proyectoDB.equals(proyecto)) {
                result = proyecto;
            } else {
                fueCambiadoPorOtroUsuario = Boolean.TRUE;
                message = rf.fromMessage("warning.otrousuarioactualizoproyectoincronizeeltablero");
                result = proyectoDB;
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
                            rf.fromMessage("print.titleproyecto"), FontFactory.getFont("arial", 12, Font.BOLD), Element.ALIGN_CENTER));

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
            PdfPTable table = new PdfPTable(6);

            // Aqui indicamos el tamaño de cada columna
            table.setTotalWidth(new float[]{60, 130, 140, 180, 60, 100});

            table.setLockedWidth(true);

            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.id"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));

            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.proyecto"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.direccion"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));

            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.cliente"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.estado"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.fechas"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));

//            table.addCell(
//                    ReportUtils.PdfCell(
//                            rf.fromMessage("print.active"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
//            
            //Invoca todos los registros con paginacion para imprimir
            Integer pageActual = 1;
            Integer totalPage = paginator.getNumberOfPage();
            //Carga los registros por paginacion
            for (int i = 1; i <= totalPage; i++) {

                pageActual = i;

                List<Proyecto> list = proyectoServices.lookup(paginator.getFilter(),
                        paginator.getSort(), pageActual, rowPageSmall.get());

                if (list == null || list.isEmpty()) {

                } else {
                    list.forEach(c -> {

                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getId().toString(), FontFactory.getFont("arial", 10, Font.NORMAL)));

                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getProyecto(), FontFactory.getFont("arial", 9, Font.NORMAL)));
                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getDireccion(), FontFactory.getFont("arial", 9, Font.NORMAL)));

                        if (c.getProyecto() == null || c.getProyecto() == null) {
                            table.addCell(
                                    ReportUtils.PdfCell(
                                            " ", FontFactory.getFont("arial", 9, Font.NORMAL)));
                        } else {
                            String representanteValue = "";
                            String sedeValue = "";
                            if (isJuridico(c.getCliente())) {
                                representanteValue += "\n" + rf.fromMessage("field.representante") + " " + c.getRepresentante().getNombre();
                                representanteValue += "\n" + rf.fromMessage("field.cedula") + " " + c.getRepresentante().getCedula();
                                representanteValue += "\n" + rf.fromMessage("field.dv") + " " + c.getRepresentante().getDv();
                                representanteValue += "\n" + rf.fromMessage("field.telefono") + " " + c.getRepresentante().getTelefono();
                                representanteValue += "\n" + rf.fromMessage("field.email") + " " + c.getRepresentante().getEmail();

                                sedeValue += "\n" + rf.fromMessage("field.sede") + " " + c.getSede().getNombre();
                            } else {
                                representanteValue += "\n" + rf.fromMessage("field.cedula") + " " + c.getCliente().getCedularuc();
                                representanteValue += "\n" + rf.fromMessage("field.dv") + " " + c.getCliente().getDv();
                                representanteValue += "\n" + rf.fromMessage("field.telefono") + " " + c.getCliente().getTelefono();
                                representanteValue += "\n" + rf.fromMessage("field.celular") + " " + c.getCliente().getCelular();
                                representanteValue += "\n" + rf.fromMessage("field.email") + " " + c.getCliente().getEmail();
                            }
                            table.addCell(
                                    ReportUtils.PdfCell(
                                            c.getCliente().getName() + sedeValue + representanteValue, FontFactory.getFont("arial", 9, Font.NORMAL)));
                        }

                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getEstado(), FontFactory.getFont("arial", 9, Font.NORMAL)));

                        String dateInicial = showDate(c.getFechaincial()) + " " + showHour(c.getFechaincial());
                        String dateFinal = showDate(c.getFechafinal()) + " " + showHour(c.getFechafinal());
                        table.addCell(
                                ReportUtils.PdfCell(
                                        dateInicial + " \na\n" + dateFinal, FontFactory.getFont("arial", 9, Font.NORMAL)));

//
//                        String activo = "";
//                        if (c.getActive() != null) {
//                            if (c.getActive()) {
//                                activo = "si";
//                            } else {
//                                activo = "no";
//                            }
//                        }
//
//                        table.addCell(
//                                ReportUtils.PdfCell(activo, FontFactory.getFont("arial", 10, Font.NORMAL)));
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
    // <editor-fold defaultstate="collapsed" desc="List<Proyecto> completeProyecto(String query)">
    public List<Proyecto> completeProyecto(String query) {

        List<Proyecto> result = new ArrayList<>();
        try {
            query = query.trim();
            List<Proyecto> proyectos = proyectoServices.likeByProyecto(query);
            if (proyectos == null || proyectos.isEmpty()) {

            } else {
                /*
                Verifica que sea del departamento
                 */

                proyectos.stream().filter(p -> (p.getDepartamentView().getIddepartament().equals(profileLogged.getDepartamentView().getIddepartament()))).forEachOrdered(p -> {
                    result.add(p);
                });

            }

            proyectoConverterServices.add(result.subList(0,
                    calcularConverterMaxNumberOfElements(result.size(), converterMaxNumberOfElements.get()))
            );
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="  autocompleteSelectedEvent(SelectEvent event)">
    public void autocompleteSelectedEvent(SelectEvent<Proyecto> event) {
        try {

            findByIdProyecto();
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="void autocompleteSelectedEventClienteProyectoFilter(SelectEvent<Proyecto> event)">

    public void autocompleteSelectedEventClienteProyectoFilter(SelectEvent<Proyecto> event) {
        try {

            findByIdClienteProyecto();
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

    // <editor-fold defaultstate="collapsed" desc="  autocompleteSelectedEvent(SelectEvent<Cliente> event)">
    public void autocompleteSelectedEventCliente(SelectEvent<Cliente> event) {
        try {

            clienteSelectedAutocomplete.getIdcliente();
            proyectoSelected.setCliente(clienteSelectedAutocomplete);
            /**
             * Alamacena los valores para usar en el converter del valor
             * embebido
             */
            if (isJuridico(clienteSelectedAutocomplete)) {
                JmoordbCoreContext.put("sedeConverter", clienteSelectedAutocomplete.getSede());
                JmoordbCoreContext.put("representanteConverter", clienteSelectedAutocomplete.getRepresentante());
            }

            if (buttonNewPressed) {
                proyectoSelected.setProyecto(rf.fromMessage("label.proyectode") + ": " + clienteSelectedAutocomplete.getName());
                proyectoSelected.setDescripcion(rf.fromMessage("label.descripcionde") + ": " + clienteSelectedAutocomplete.getName());
                proyectoSelected.setDireccion(clienteSelectedAutocomplete.getDireccion());
                proyectoSelected.setTelefono(clienteSelectedAutocomplete.getTelefono());
                proyectoSelected.setEmail(clienteSelectedAutocomplete.getEmail());
                proyectoSelected.setFax(clienteSelectedAutocomplete.getFax());
                proyectoSelected.setTipocliente(clienteSelectedAutocomplete.getTipocliente());
            } else {

            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }// </editor-fold>
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

    public Boolean isJuridico(Cliente cliente) {
        Boolean result = Boolean.FALSE;
        try {
            if (cliente == null || cliente.getIdcliente() == null) {
                return result;
            }
            if (cliente.getTipocliente().equals(rf.fromMessage("selectonemenu.juridico"))) {
                result = Boolean.TRUE;
            }
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }

}
