package com.ayantsoft.interview.jsf.view;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.ayantsoft.expense.service.CandidateService;
import com.ayantsoft.interview.hibernate.pojo.Candidate;
import com.ayantsoft.interview.hibernate.pojo.ContactAdddress;
import com.ayantsoft.interview.hibernate.pojo.Doument;
import com.ayantsoft.interview.hibernate.pojo.JobConsultancy;
import com.ayantsoft.interview.jsf.model.LazyCandidateDataModel;

@ManagedBean
@ViewScoped
public class CandidateBean
implements Serializable {
	
	/*
	 * serialVersionUID
	 */
	
    private static final long serialVersionUID = -7616875129322432820L;
    
    private String action;
    private Candidate candidate;
    private ContactAdddress contactAdddress;
    private JobConsultancy jobConsultancy;
    private Doument document;
    private UploadedFile uplodedfile;
    private boolean enableAttach;
    private boolean disable;
    private boolean disableAssmnt;
    private boolean render;
    private String fileNames;
    private String filePath;
    
    private LazyDataModel<Candidate> candidateLazyModel;
    private List<JobConsultancy> jobConsultancyList = new ArrayList<JobConsultancy>();
    
    @ManagedProperty(value="#{candidateService}")
    private CandidateService candidateService;

    @PostConstruct
    public void init() {
        this.candidateLazyModel = new LazyCandidateDataModel(this.candidateService);
    }

    public void newCandidate() {
        this.action = "NEW";
        if (this.jobConsultancyList != null) {
            this.jobConsultancyList.clear();
        }
        this.document = new Doument();
        this.candidate = new Candidate();
        this.candidate.setContactAdddress(new ContactAdddress());
        this.candidate.setJobConsultancy(new JobConsultancy());
        this.jobConsultancyList = this.candidateService.getConsultancyName();
        this.disable = false;
        this.render = true;
        this.disableAssmnt = true;
    }

    public void listCandidate() {
        if (this.candidate != null) {
            this.candidate.setContactAdddress(new ContactAdddress());
        }
        this.action = "LISTCANDIDATE";
    }

    public void onRowSelect() {
        this.action = "NEW";
        this.jobConsultancyList = this.candidateService.getConsultancyName();
        Candidate tmpCandidate = this.candidateService.findById(this.candidate.getId());
        this.document = tmpCandidate.getDoument();
        this.disableAssmnt = false;
        this.disable = true;
    }

    public void enanleBtn() {
        this.disable = false;
    }

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UploadedFile file = (UploadedFile)value;
        if (file.getSize() == 0) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Upload the File"));
        }
    }

    public void saveCandidate() {
        try {
            this.candidateService.saveCandidate(this.candidate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Candidate saved successfully"));
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Candidate not saved"));
        }
    }

    public void saveAssement() {
        try {
            Boolean status = this.candidateService.saveAssmnt(this.candidate);
            if (status.booleanValue()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success : ", "Assesment Saved Successfully"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error : ", "Assesment Not Saved"));
            }
        }
        catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Exception Occured : ", "Assesment Not Saved"));
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        this.uplodedfile = event.getFile();
        if (this.uplodedfile != null && this.uplodedfile.getSize() > 0) {
            Properties properties = new Properties();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("uploadFile.properties");
            if (inputStream == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Unable to upload File"));
                return;
            }
            properties.load(inputStream);
            this.filePath = properties.getProperty("filePath");
            File file = new File(this.filePath + this.candidate.getId());
            file.mkdirs();
            if (this.candidate.getDoument() == null) {
                this.document = new Doument();
            } else {
                File oldFile = new File(this.document.getFilePath());
                oldFile.delete();
                this.document = new Doument();
            }
            String fullPath = null;
            byte[] bytes = null;
            if (event.getFile() != null) {
                bytes = this.uplodedfile.getContents();
                this.fileNames = FilenameUtils.getName((String)this.uplodedfile.getFileName());
                fullPath = this.filePath + this.candidate.getId() + "/" + this.fileNames;
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(fullPath)));
                stream.write(bytes);
                stream.close();
            }
            this.document.setFileName(this.fileNames);
            this.document.setFilePath(fullPath);
            this.document.setCreatedDate(new Date());
            this.candidate.setDoument(this.document);
            this.candidateService.saveFile(this.document, this.candidate);
            Candidate tmpCandidate = this.candidateService.findById(this.candidate.getId());
            this.document = tmpCandidate.getDoument();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "File Uploaded Succesfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Unable to upload File"));
        }
    }

    public void openFile() {
        if (this.document.equals(null)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "File Type Not Found"));
        } else {
            try {
                File file = new File(this.document.getFilePath());
                HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
                if (this.document.getFileName().endsWith("pdf")) {
                    response.setContentType("application/pdf");
                } else if (this.document.getFileName().endsWith("doc")) {
                    response.setContentType("application/doc");
                } else if (this.document.getFileName().endsWith("docx")) {
                    response.setContentType("application/docx");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "File Type Not Supported"));
                }
                response.setHeader("Content-Disposition", "inline;filename=\"" + this.document.getFileName() + "\"");
                ServletOutputStream outputStream = response.getOutputStream();
                byte[] bFile = new byte[(int)file.length()];
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(bFile);
                IOUtils.copy((InputStream)inputStream, (OutputStream)outputStream);
                outputStream.flush();
                outputStream.close();
                FacesContext.getCurrentInstance().responseComplete();
            }
            catch (IOException e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error" + e.getMessage()));
            }
        }
    }

    public void emailValidate(FacesContext context, UIComponent component, Object value) {
        try {
            boolean hasEmail;
            if (this.candidate.getId() == null && !(hasEmail = this.candidateService.checkUniqueEmail((String)value, this.candidate.getId()))) {
                FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email duplicate:", "Email already exists"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error" + e.getMessage()));
        }
    }

    public void workPhoneValidate(FacesContext context, UIComponent component, Object value) {
        try {
            if (this.candidate.getId() == null) {
                boolean hasPhone = this.candidateService.checkUniquePhone((String)value, this.candidate.getId());
                if (!hasPhone) {
                    FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone duplicate:", "Phone already exists"));
                }
            } else {
                boolean hasPhone = this.candidateService.checkUniquePhone((String)value, this.candidate.getId());
                if (!hasPhone) {
                    FacesContext.getCurrentInstance().addMessage(component.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Phone duplicate:", "Phone already exists"));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error" + e.getMessage()));
        }
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isDisableAssmnt() {
        return this.disableAssmnt;
    }

    public void setDisableAssmnt(boolean disableAssmnt) {
        this.disableAssmnt = disableAssmnt;
    }

    public boolean isRender() {
        return this.render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }

    public boolean isDisable() {
        return this.disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isEnableAttach() {
        return this.enableAttach;
    }

    public void setEnableAttach(boolean enableAttach) {
        this.enableAttach = enableAttach;
    }

    public String getFileNames() {
        return this.fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }

    public List<JobConsultancy> getJobConsultancyList() {
        return this.jobConsultancyList;
    }

    public void setJobConsultancyList(List<JobConsultancy> jobConsultancyList) {
        this.jobConsultancyList = jobConsultancyList;
    }

    public JobConsultancy getJobConsultancy() {
        return this.jobConsultancy;
    }

    public void setJobConsultancy(JobConsultancy jobConsultancy) {
        this.jobConsultancy = jobConsultancy;
    }

    public LazyDataModel<Candidate> getCandidateLazyModel() {
        return this.candidateLazyModel;
    }

    public void setCandidateLazyModel(LazyDataModel<Candidate> candidateLazyModel) {
        this.candidateLazyModel = candidateLazyModel;
    }

    public UploadedFile getUplodedfile() {
        return this.uplodedfile;
    }

    public void setUplodedfile(UploadedFile uplodedfile) {
        this.uplodedfile = uplodedfile;
    }

    public CandidateService getCandidateService() {
        return this.candidateService;
    }

    public void setCandidateService(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    public Candidate getCandidate() {
        return this.candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public ContactAdddress getContactAdddress() {
        return this.contactAdddress;
    }

    public void setContactAdddress(ContactAdddress contactAdddress) {
        this.contactAdddress = contactAdddress;
    }

    public Doument getDocument() {
        return this.document;
    }

    public void setDocument(Doument document) {
        this.document = document;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}