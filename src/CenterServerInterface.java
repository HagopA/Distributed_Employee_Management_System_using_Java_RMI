public interface CenterServerInterface<T>
{
    public String createMRecord(String firstName, String lastName, int empId, String mailId, ProjectInfo project);

    public String createERecord(String firstName, String lastName, int empId, String mailId, String projectId, String location);

    public String getRecordCounts();

    public String editRecord(String recordId, String fieldName, T newValue);
}
