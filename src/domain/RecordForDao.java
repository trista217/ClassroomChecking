package domain;


public class RecordForDao {

	private String recordRoomId;
	private String recordDate;
	private String bestOverlap;
	private float bestOverlapFloat;
	private String bestAvailableStartTime;
	private String bestAvailableEndTime;

	private String recordType;
	private String recordStartTime;
	private String recordEndTime;
	private String recordOverlap;
	private String recordName;
	private String recordDepartment;
	private String recordStatus;
	private String recordContent;

	public RecordForDao() {
		super();
	}

	public RecordForDao(String recordType, String recordStartTime,
			String recordEndTime, String recordOverlap, String recordName,
			String recordDepartment, String recordStatus, String recordContent) {

		this.recordType = recordType;
		this.recordStartTime = recordStartTime;
		this.recordEndTime = recordEndTime;
		this.recordOverlap = recordOverlap;
		this.recordName = recordName;
		this.recordDepartment = recordDepartment;
		this.recordStatus = recordStatus;
		this.recordContent = recordContent;
	}

	public RecordForDao(String recordStartTime, String recordEndTime,
			String recordOverlap) {

		this.recordType = "空闲";
		this.recordStartTime = recordStartTime;
		this.recordEndTime = recordEndTime;
		this.recordOverlap = recordOverlap;
	}

	/*
	 * String recordLine = recordResult + ";" + recordStartTime + ";" +
	 * recordEndTime + ";" + recordOverlap + ";" + recordName + ";" +
	 * recordDepartment + ";" + recordState + ";" + recordContent;
	 */

	public void setSharedValues(String recordRoomId, String recordDate,
			String bestOverlap, float bestOverlapFloat,
			String bestAvailableStartTime, String bestAvailableEndTime) {
		this.recordRoomId = recordRoomId;
		this.recordDate = recordDate;
		this.bestOverlap = bestOverlap;
		this.bestOverlapFloat = bestOverlapFloat;
		this.bestAvailableStartTime = bestAvailableStartTime;
		this.bestAvailableEndTime = bestAvailableEndTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

	public String getRecordRoomId() {
		return recordRoomId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "record [recordRoomId=" + recordRoomId + ", recordDate="
				+ recordDate + ", bestOverlap=" + bestOverlap
				+ ", bestOverlapFloat=" + bestOverlapFloat
				+ ", bestAvailableStartTime=" + bestAvailableStartTime
				+ ", bestAvailableEndTime=" + bestAvailableEndTime
				+ ", recordType=" + recordType + ", recordStartTime="
				+ recordStartTime + ", recordEndTime=" + recordEndTime
				+ ", recordOverlap=" + recordOverlap + ", recordName="
				+ recordName + ", recordDepartment=" + recordDepartment
				+ ", recordStatus=" + recordStatus + ", recordContent="
				+ recordContent + "]";
	}

	public String getRecordDate() {
		return recordDate;
	}

	public String getBestOverlap() {
		return bestOverlap;
	}

	public float getBestOverlapFloat() {
		return bestOverlapFloat;
	}

	public String getBestAvailableStartTime() {
		return bestAvailableStartTime;
	}

	public String getBestAvailableEndTime() {
		return bestAvailableEndTime;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setAvailable(String recordType) {
		this.recordType = recordType;
	}

	public String getRecordStartTime() {
		return recordStartTime;
	}

	public void setRecordStartTime(String recordStartTime) {
		this.recordStartTime = recordStartTime;
	}

	public String getRecordEndTime() {
		return recordEndTime;
	}

	public void setRecordEndTime(String recordEndTime) {
		this.recordEndTime = recordEndTime;
	}

	public String getRecordOverlap() {
		return recordOverlap;
	}

	public void setRecordOverlap(String recordOverlap) {
		this.recordOverlap = recordOverlap;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRecordDepartment() {
		return recordDepartment;
	}

	public void setRecordDepartment(String recordDepartment) {
		this.recordDepartment = recordDepartment;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getRecordContent() {
		return recordContent;
	}

	public void setRecordContent(String recordContent) {
		this.recordContent = recordContent;
	}
}
