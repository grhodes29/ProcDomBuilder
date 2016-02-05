package builder;

public class MetaRow {
	
	
	private String _parent_table;
	private String _name;
	private String _data_type;
	private Integer _max_length;
	private Integer _precision;
	private Integer _scale;
	private Boolean _isnullable;
	private Boolean _isprimarykey;
	
	
	/**
	 * @return the _parent_table
	 */
	public String get_parent_table() {
		
		return _parent_table;
	}
	/**
	 * @param _parent_table the _parent_table to set
	 */
	public void set_parent_table(String _parent_table) {
		this._parent_table = _parent_table;
	}
	/**
	 * @return the _name
	 */
	public String get_name() {
		return _name;
	}
	/**
	 * @param _name the _name to set
	 */
	public void set_name(String _name) {
		this._name = _name;
	}
	/**
	 * @return the _data_type
	 */
	public String get_data_type() {
		return _data_type;
	}
	/**
	 * @param _data_type the _data_type to set
	 */
	public void set_data_type(String _data_type) {
		this._data_type = _data_type;
	}
	/**
	 * @return the _max_length
	 */
	public Integer get_max_length() {
		return _max_length;
	}
	/**
	 * @param _max_length the _max_length to set
	 */
	public void set_max_length(Integer _max_length) {
		this._max_length = _max_length;
	}
	/**
	 * @return the _precision
	 */
	public Integer get_precision() {
		return _precision;
	}
	/**
	 * @param _precision the _precision to set
	 */
	public void set_precision(Integer _precision) {
		this._precision = _precision;
	}
	/**
	 * @return the _scale
	 */
	public Integer get_scale() {
		return _scale;
	}
	/**
	 * @param _scale the _scale to set
	 */
	public void set_scale(Integer _scale) {
		this._scale = _scale;
	}
	/**
	 * @return the _isnullable
	 */
	public Boolean get_isnullable() {
		return _isnullable;
	}
	/**
	 * @param _isnullable the _isnullable to set
	 */
	public void set_isnullable(Boolean _isnullable) {
		this._isnullable = _isnullable;
	}
	/**
	 * @return the _isprimarykey
	 */
	public Boolean get_isprimarykey() {
		return _isprimarykey;
	}
	/**
	 * @param _isprimarykey the _isprimarykey to set
	 */
	public void set_isprimarykey(Boolean _isprimarykey) {
		this._isprimarykey = _isprimarykey;
	}



}
