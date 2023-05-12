package task;

public class Task {
    protected String nameTask;
    protected String descriptionTask;
    protected String statusTask;
    protected int idTask;

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setStatusTask(String statusTask) {
        this.statusTask = statusTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public String getStatusTask() {
        return statusTask;
    }

    @Override
    public String toString() {
        return "\n" + "Имя задачи: '" + nameTask + "', описание задачи: '" + descriptionTask + "', статус задачи: '"
                + statusTask + "', ИД задачи: '" + idTask + "'";
    }
}