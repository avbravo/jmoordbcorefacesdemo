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
import com.nerys.converter.services.TipoPruebaConverterServices;
import com.nerys.faces.services.FacesServices;

import com.nerys.model.ActionHistory;
import com.nerys.model.ObservacionTipoPrueba;
import com.nerys.services.implementation.ColorManagementImpl;
import com.nerys.model.Profile;
import com.nerys.model.Proyecto;
import com.nerys.model.TipoPrueba;
import com.nerys.model.User;
import jakarta.annotation.PostConstruct;
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
import com.nerys.services.TipoPruebaServices;

import jakarta.annotation.PreDestroy;
import jakarta.faces.view.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.ReorderEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author avbravo
 */
@Named
@ViewScoped

@Data
public class TipoPruebaFaces implements Serializable, JmoordbCoreXHTMLUtil, IPaginator,  FacesServices {
// <editor-fold defaultstate="collapsed" desc="ConverterServices">


 @Inject
    TipoPruebaConverterServices tipoPruebaConverterServices;
// </editor-fold
 
    // <editor-fold defaultstate="collapsed" desc=" fields">
    private static final long serialVersionUID = 1L;
    private User userLogged = new User();
    private String message = "";
    private String tituloDialogo = "";
    private Boolean isOverlayPanelOpen = Boolean.FALSE;
    private Boolean isButtonSavePressed = Boolean.TRUE;
    private Boolean fueCambiadoPorOtroUsuario = Boolean.FALSE;

    private Boolean buttonNewPressed = Boolean.TRUE;
    private Boolean showDialogContent = Boolean.TRUE;

    // Invocados desde otro fomulario
    private Boolean isEditable = Boolean.TRUE;
    private String callerLevel0 = "";
    private Profile profileLogged = new Profile();

    private TipoPrueba tipoPruebaSelected = new TipoPrueba();
    private TipoPrueba tipoPruebaSelectedAutocomplete = new TipoPrueba();
    private TipoPrueba tipoPruebaDB = new TipoPrueba();
    private Proyecto proyectoSelected = new Proyecto();

    private List<ResponsiveOption> responsiveOptions;

    private List<TipoPrueba> tipoPruebaList = new ArrayList<>();
    

    private Boolean haveTipoPruebaOpen = Boolean.FALSE;
    private Boolean isRowPageSmall = Boolean.TRUE;

    ColorManagement colorKnob = new ColorManagementImpl();
    private DataTable dataTable;
    Integer totalRecords = 0;
    
    List<ObservacionTipoPrueba> observacionTipoPrueba = new ArrayList<>();

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="selected For Dialog()">
    public TipoPrueba getTipoPruebaSelected() {
        if (tipoPruebaSelected == null) {

            tipoPruebaSelected = new TipoPrueba();
        }

        return tipoPruebaSelected;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="LazyDataModel">
    private LazyDataModel<TipoPrueba> tipoPruebaLazyDataModel;
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
    TipoPruebaServices tipoPruebaServices;
     
 
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
            haveTipoPruebaOpen = Boolean.FALSE;
            message = "";
observacionTipoPrueba = new ArrayList<>();
            userLogged = (User) JmoordbCoreContext.get("LoginFaces.userLogged");
            profileLogged = (Profile) JmoordbCoreContext.get("LoginFaces.profileLogged");
            tipoPruebaSelectedAutocomplete = new TipoPrueba();
            saveToMediaContext(userLogged.getPhoto());
            responsiveOptions = new ArrayList<>();
            responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
            responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
            responsiveOptions.add(new ResponsiveOption("560px", 1, 1));

            findAll();

            this.tipoPruebaLazyDataModel = new LazyDataModel<TipoPrueba>() {
                @Override
                public List<TipoPrueba> load(int offset, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {

                    switch (paginator.getName()) {
                        case "findAll":

                            totalRecords = tipoPruebaServices.count(paginator.getFilter(),
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

                    List<TipoPrueba> result = new ArrayList<>();
                    switch ((paginator.getName())) {
                        case "findAll":

                            result = tipoPruebaServices.lookup(paginator.getFilter(),
                                    paginator.getSort(), paginator.getPage(), rowPageSmall.get());

                            break;
                        default:

                    }

                    tipoPruebaLazyDataModel.setRowCount(totalRecords);

                    PrimeFaces.current().executeScript("setDataTableWithPageStart()");
                    PrimeFaces.current().executeScript("widgetVardataTable.getPaginator().setPage(0);");
                    tipoPruebaList = result;

                    return result;
                }

                @Override
                public int count(Map<String, FilterMeta> map) {

                    return totalRecords;

                }

                @Override
                public String getRowKey(TipoPrueba object) {
                    if (object == null || object.getIdtipoprueba() == null) {
                        return "";
                    }
                    return object.getIdtipoprueba().toString();
                }

                @Override
                public TipoPrueba getRowData(String rowKey) {
                    for (TipoPrueba t : tipoPruebaList) {
                        if (t != null) {
                            if (t.getIdtipoprueba().equals(rowKey)) {
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
        tipoPruebaConverterServices.destroyed();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String findAll()">
    public String findAll() {
        try {

            Bson filter = ne("tipoPrueba", "");

            Document sort = new Document("tipoPrueba", 1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("idtipoPrueba", -1)))
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
    // <editor-fold defaultstate="collapsed" desc="String findByIdUser()">

    public String findByIdTipoPrueba() {
        try {

            Bson filter0 = or(eq("active", Boolean.FALSE),
                    eq("active", Boolean.TRUE)
            );
            Bson filter = and(filter0, eq("idtipoprueba", tipoPruebaSelectedAutocomplete.getIdtipoprueba()));
            Document sort = new Document("tipoPrueba", 1);

            paginator
                    = new Paginator.Builder()
                            .page(1)
                            .filter(filter)
                            .sort(sort)
                            .sorted(new Sorted(new Document("tipoPrueba", -1)))
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

    // <editor-fold defaultstate="collapsed" desc="save(TipoPrueba tipoPrueba)">
    public void save(TipoPrueba tipoPrueba) {
        try {

            tipoPruebaSelected = tipoPrueba;
            tipoPruebaSelected.setTipoPrueba(tipoPruebaSelected.getTipoPrueba().trim());
             tipoPruebaSelected.setActive(Boolean.TRUE);

            if (tipoPrueba.getTipoPrueba() == null || tipoPrueba.getTipoPrueba().equals("")) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.ingreseTipoPrueba"));
                return;
            }

            if (tipoPruebaServices.existsTipoPrueba(tipoPruebaSelected)) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.exitsotherdocumentwiththistipo"));
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

            tipoPruebaSelected.setActionHistory(actionHistoryList);

            Optional<TipoPrueba> tipoPruebaOptional = tipoPruebaServices.save(tipoPruebaSelected);
            if (!tipoPruebaOptional.isPresent()) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromCore("warning.save"));
            } else {
                tipoPruebaSelected = tipoPruebaOptional.get();
                JmoordbCoreContext.put("TipoPruebaFaces.tipoPruebaSelected", tipoPruebaSelected);
                //  FacesUtil.infoDialog(rf.fromCore("info.save"), rf.fromCore("info.save"));
                FacesUtil.successMessage(rf.fromCore("info.save"));

                /**
                 * Actualiza las tarjetas
                 */
                PrimeFaces.current().ajax().update("dataTable");

                closeOverlayPanel("PF('overlayPanelTipoPrueba').hide()");
                refresh();
            }

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return;
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="edit(TipoPrueba tipoPrueba)">

    public void edit(TipoPrueba tipoPrueba) {
        try {
            TipoPrueba tipoPruebaDBNow = tipoPruebaServices.findByIdTipoPrueba(tipoPrueba.getIdtipoprueba()).get();
            if (!tipoPruebaDBNow.equals(tipoPruebaDB)) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromMessage("warning.otrousuarioactualizodocumentosincronicesusdatosprimero"));
                return;
            }
            tipoPruebaSelected = tipoPrueba;
            tipoPruebaSelected.setTipoPrueba(tipoPruebaSelected.getTipoPrueba().trim());

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
             actionHistoryList.addAll(tipoPrueba.getActionHistory());
            actionHistoryList.add(actionHistory);

            tipoPruebaSelected.setActionHistory(actionHistoryList);

            Boolean isUpdate = tipoPruebaServices.update(tipoPruebaSelected);
            if (!isUpdate) {
                FacesUtil.warningDialog(rf.fromCore("warning.warning"), rf.fromCore("warning.update"));
            } else {

                FacesUtil.successMessage(rf.fromCore("info.update"));

                /**
                 * Actualiza las tarjetas
                 */
                //  tipoPruebaList.add(tipoPruebaSelected);
                //  PrimeFaces.current().ajax().update("dataTable");
                closeOverlayPanel("PF('overlayPanelTipoPrueba').hide()");
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

    // <editor-fold defaultstate="collapsed" desc="void agregarRowTipoPrueba()">
    public void agregarRowTipoPrueba() {
        try {
            buttonNewPressed = Boolean.TRUE;
            showDialogContent = Boolean.FALSE;

            showDialogContent = Boolean.TRUE;

            tipoPruebaSelected = new TipoPrueba();
            tipoPruebaSelected.setActive(Boolean.TRUE);
            List<ObservacionTipoPrueba> observacionTipoPruebas = new ArrayList<>();
           
         tipoPruebaSelected.setObservacionTipoPrueba(observacionTipoPruebas);
         
            isButtonSavePressed = Boolean.FALSE;
            tituloDialogo = rf.fromMessage("dialog.tipoPruebaagregar");
            PrimeFaces.current().ajax().update("outputPanelTipoPrueba");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="String refresh()">
    @Override
    public String refresh() {
        try {

            tipoPruebaList = new ArrayList<>();
            findAll();
            PrimeFaces.current().ajax().update("form");
            PrimeFaces.current().ajax().update("dataTable");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String prepareTipoPrueba(TipoPrueba tipoPrueba)">
    public String prepareTipoPrueba(TipoPrueba tipoPrueba) {
        try {
            tipoPruebaSelected = new TipoPrueba();
            showDialogContent = Boolean.TRUE;
            tipoPruebaSelected = validarSiCambioTipoPrueba(tipoPrueba);
            isOverlayPanelOpen = Boolean.TRUE;
            isButtonSavePressed = Boolean.TRUE;
            buttonNewPressed = Boolean.FALSE;
            tituloDialogo = rf.fromMessage("dialog.tipoPruebaeditar");
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="TipoPrueba validarSiCambioTipoPrueba(TipoPrueba tipoPrueba)">
    private TipoPrueba validarSiCambioTipoPrueba(TipoPrueba tipoPrueba) {
        TipoPrueba result = new TipoPrueba();
        try {

            message = "";
            fueCambiadoPorOtroUsuario = Boolean.FALSE;
            this.tipoPruebaDB = tipoPruebaServices.findByIdTipoPrueba(tipoPrueba.getIdtipoprueba()).get();

            if (tipoPruebaDB.equals(tipoPrueba)) {
                result = tipoPrueba;
            } else {
                fueCambiadoPorOtroUsuario = Boolean.TRUE;
                message = rf.fromMessage("warning.otrousuarioactualizodocumentosincronize");

                result = tipoPruebaDB;
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
        com.lowagie.text.Document document = new com.lowagie.text.Document(PageSize.LETTER);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, baos);
            // METADATA

            document.open();
            document.add(
                    ReportUtils.paragraph(
                            rf.fromMessage("print.titletipoPrueba"), FontFactory.getFont("arial", 12, Font.BOLD), Element.ALIGN_CENTER));

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
            PdfPTable table = new PdfPTable(4);

            // Aqui indicamos el tamaño de cada columna
            table.setTotalWidth(new float[]{60, 120,125, 60});

            table.setLockedWidth(true);

            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.idtipoprueba"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.tipoPrueba"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));
            table.addCell(
                    ReportUtils.PdfCell(
                            rf.fromMessage("print.observaciones"), FontFactory.getFont("arial", 11, Font.BOLD), Element.ALIGN_CENTER));

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

                List<TipoPrueba> list = tipoPruebaServices.lookup(paginator.getFilter(),
                        paginator.getSort(), pageActual, rowPageSmall.get());

                if (list == null || list.isEmpty()) {

                } else {
                    list.forEach(c -> {

                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getIdtipoprueba().toString(), FontFactory.getFont("arial", 10, Font.NORMAL)));
                        table.addCell(
                                ReportUtils.PdfCell(
                                        c.getTipoPrueba(), FontFactory.getFont("arial", 9, Font.NORMAL)));
                        
                        
                            String subproyecto = "";
                        Boolean first = Boolean.TRUE;
                        String observaciones="";
                        for (ObservacionTipoPrueba otp : c.getObservacionTipoPrueba()) {
                            if (first) {
                               observaciones += otp.getObservacion();
                                first = Boolean.FALSE;
                            } else {
                                observaciones  += "/ " + otp.getObservacion();
                            }

                        }
                        
                          table.addCell(
                           ReportUtils.PdfCell(observaciones, FontFactory.getFont("arial", 10, Font.NORMAL)));
                        
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
    // <editor-fold defaultstate="collapsed" desc="List<TipoPrueba> completeTipoPrueba(String query)">
    public List<TipoPrueba> completeTipoPrueba(String query) {

        List<TipoPrueba> result = new ArrayList<>();
        try {
            query = query.trim();
            result = tipoPruebaServices.likeByTipoPrueba(query);
 tipoPruebaConverterServices.add(result.subList(0,                     
                    calcularConverterMaxNumberOfElements(result.size(),converterMaxNumberOfElements.get()))
            );
        } catch (Exception e) {

            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

        return result;
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="  autocompleteSelectedEvent(SelectEvent event)">
    public void autocompleteSelectedEvent(SelectEvent<TipoPrueba> event) {
        try {

            findByIdTipoPrueba();
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="void autocompleteUnselectListener(UnselectEvent event)">
    public void autocompleteUnselectListener(UnselectEvent event) {

    }
    // </editor-fold>
    
     // <editor-fold defaultstate="collapsed" desc="handleCloseDialogrefresh(CloseEvent event)">
    public void handleCloseDialogRefresh(CloseEvent event) {
        try {
           refresh();
          
        } catch (Exception e) {
              FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }
    // </editor-fold>

    
      // <editor-fold defaultstate="collapsed" desc="void onCellEditTarea(CellEditEvent event)">
    public void onCellEditObservacion(CellEditEvent event) {

        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {

         
        }
        //    PrimeFaces.current().ajax().update("datatableTareaFooter");

    }

    // </editor-fold>
    

    public void onRowReorderObservacion(ReorderEvent event) {
        try {
            int from = event.getFromIndex();
            int to = event.getToIndex();

          
            
        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }
    
    
     // <editor-fold defaultstate="collapsed" desc="void deleteObservacionTipoPrueba(ObservacionTipoPrueba observacionTipoPrueba)">
    public void deleteObservacionTipoPrueba(ObservacionTipoPrueba observacionTipoPrueba) {
        try {
            isButtonSavePressed = Boolean.FALSE;
            this.tipoPruebaSelected.getObservacionTipoPrueba().remove(observacionTipoPrueba);

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }

    }

    // </editor-fold>
    

   // <editor-fold defaultstate="collapsed" desc="void agregarRowObservacionTipoPrueba()">

    public void agregarRowObservacionTipoPrueba() {
        try {
           
            ObservacionTipoPrueba observacionTipoPrueba = new ObservacionTipoPrueba(0L, "", Boolean.TRUE);
               
            tipoPruebaSelected.getObservacionTipoPrueba().add(observacionTipoPrueba  );

            isButtonSavePressed = Boolean.FALSE;
         
            PrimeFaces.current().ajax().update("dataTableObservacion");

        } catch (Exception e) {
            FacesUtil.errorMessage(FacesUtil.nameOfClassAndMethod() + " " + e.getLocalizedMessage());
        }
    }

    // </editor-fold>
}
