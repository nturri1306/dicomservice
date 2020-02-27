package it.dromedian;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {

	//alt shit s+r genera i set e get
 //   private List<String> servers = new ArrayList<>();




    public String getGdcm_path() {
		return gdcm_path;
	}
	public void setGdcm_path(String gdcm_path) {
		this.gdcm_path = gdcm_path;
	}
	public String getAetitle() {
		return aetitle;
	}
	public void setAetitle(String aetitle) {
		this.aetitle = aetitle;
	}
	public String getPort_scp() {
		return port_scp;
	}
	public void setPort_scp(String port_scp) {
		this.port_scp = port_scp;
	}
	public String getAe_call() {
		return ae_call;
	}
	public void setAe_call(String ae_call) {
		this.ae_call = ae_call;
	}
	public String getAe_port() {
		return ae_port;
	}
	public void setAe_port(String ae_port) {
		this.ae_port = ae_port;
	}
	public String getAe_host() {
		return ae_host;
	}
	public void setAe_host(String ae_host) {
		this.ae_host = ae_host;
	}
	public String getDicom_storage() {
		return dicom_storage;
	}
	public void setDicom_storage(String dicom_storage) {
		this.dicom_storage = dicom_storage;
	}
	public String getCmd_os() {
		return cmd_os;
	}
	public void setCmd_os(String cmd_os) {
		this.cmd_os = cmd_os;
	}
	public String getDb_url() {
		return db_url;
	}
	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}
	public String getDb_user() {
		return db_user;
	}
	public void setDb_user(String db_user) {
		this.db_user = db_user;
	}
	public String getDb_pwd() {
		return db_pwd;
	}
	public void setDb_pwd(String db_pwd) {
		this.db_pwd = db_pwd;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip =ip;
	}

	/*public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port =port;
	}*/

	private  String gdcm_path;
	private  String aetitle;
	private  String port_scp;
	private  String ae_call;
	private  String ae_port;
	private  String ae_host;
	private  String dicom_storage;
	private  String cmd_os;
	private  String db_url;
	private  String db_user;
	private  String db_pwd;
	private  String ip="127.0.0.1";



}