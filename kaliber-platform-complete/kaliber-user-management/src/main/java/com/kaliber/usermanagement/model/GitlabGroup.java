package com.kaliber.usermanagement.model;

public class GitlabGroup {

    private int id;
    private String web_url;
    private String name;
    private String path;
    private String description;
    private String visibility;
    private Boolean lfs_enabled;
    private String avatar_url;
    private Boolean request_access_enabled;
    private String full_name;
    private String full_path;
    private int parent_id;
    public GitlabGroup(int id, String web_url, String name, String path, String description, String visibility,
                       Boolean lfs_enabled, String avatar_url, Boolean request_access_enabled, String full_name, String full_path,
                       int parent_id) {
        super();
        this.id = id;
        this.web_url = web_url;
        this.name = name;
        this.path = path;
        this.description = description;
        this.visibility = visibility;
        this.lfs_enabled = lfs_enabled;
        this.avatar_url = avatar_url;
        this.request_access_enabled = request_access_enabled;
        this.full_name = full_name;
        this.full_path = full_path;
        this.parent_id = parent_id;
    }
    public GitlabGroup() {
        super();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWeb_url() {
        return web_url;
    }
    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getVisibility() {
        return visibility;
    }
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }
    public Boolean getLfs_enabled() {
        return lfs_enabled;
    }
    public void setLfs_enabled(Boolean lfs_enabled) {
        this.lfs_enabled = lfs_enabled;
    }
    public String getAvatar_url() {
        return avatar_url;
    }
    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    public Boolean getRequest_access_enabled() {
        return request_access_enabled;
    }
    public void setRequest_access_enabled(Boolean request_access_enabled) {
        this.request_access_enabled = request_access_enabled;
    }
    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getFull_path() {
        return full_path;
    }
    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }
    public int getParent_id() {
        return parent_id;
    }
    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
    @Override
    public String toString() {
        return "GitlabGroup [id=" + id + ", web_url=" + web_url + ", name=" + name + ", path=" + path + ", description="
                + description + ", visibility=" + visibility + ", lfs_enabled=" + lfs_enabled + ", avatar_url="
                + avatar_url + ", request_access_enabled=" + request_access_enabled + ", full_name=" + full_name
                + ", full_path=" + full_path + ", parent_id=" + parent_id + "]";
    }

}
