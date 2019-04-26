/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2019-04-22")
public class FileInfo implements org.apache.thrift.TBase<FileInfo, FileInfo._Fields>, java.io.Serializable, Cloneable, Comparable<FileInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("FileInfo");

  private static final org.apache.thrift.protocol.TField FILE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("fileName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField FILE_CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("fileContent", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField FILE_VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("fileVersion", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new FileInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new FileInfoTupleSchemeFactory());
  }

  public String fileName; // required
  public String fileContent; // required
  public int fileVersion; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FILE_NAME((short)1, "fileName"),
    FILE_CONTENT((short)2, "fileContent"),
    FILE_VERSION((short)3, "fileVersion");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // FILE_NAME
          return FILE_NAME;
        case 2: // FILE_CONTENT
          return FILE_CONTENT;
        case 3: // FILE_VERSION
          return FILE_VERSION;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __FILEVERSION_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FILE_NAME, new org.apache.thrift.meta_data.FieldMetaData("fileName", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_CONTENT, new org.apache.thrift.meta_data.FieldMetaData("fileContent", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FILE_VERSION, new org.apache.thrift.meta_data.FieldMetaData("fileVersion", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(FileInfo.class, metaDataMap);
  }

  public FileInfo() {
  }

  public FileInfo(
    String fileName,
    String fileContent,
    int fileVersion)
  {
    this();
    this.fileName = fileName;
    this.fileContent = fileContent;
    this.fileVersion = fileVersion;
    setFileVersionIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public FileInfo(FileInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetFileName()) {
      this.fileName = other.fileName;
    }
    if (other.isSetFileContent()) {
      this.fileContent = other.fileContent;
    }
    this.fileVersion = other.fileVersion;
  }

  public FileInfo deepCopy() {
    return new FileInfo(this);
  }

  @Override
  public void clear() {
    this.fileName = null;
    this.fileContent = null;
    setFileVersionIsSet(false);
    this.fileVersion = 0;
  }

  public String getFileName() {
    return this.fileName;
  }

  public FileInfo setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public void unsetFileName() {
    this.fileName = null;
  }

  /** Returns true if field fileName is set (has been assigned a value) and false otherwise */
  public boolean isSetFileName() {
    return this.fileName != null;
  }

  public void setFileNameIsSet(boolean value) {
    if (!value) {
      this.fileName = null;
    }
  }

  public String getFileContent() {
    return this.fileContent;
  }

  public FileInfo setFileContent(String fileContent) {
    this.fileContent = fileContent;
    return this;
  }

  public void unsetFileContent() {
    this.fileContent = null;
  }

  /** Returns true if field fileContent is set (has been assigned a value) and false otherwise */
  public boolean isSetFileContent() {
    return this.fileContent != null;
  }

  public void setFileContentIsSet(boolean value) {
    if (!value) {
      this.fileContent = null;
    }
  }

  public int getFileVersion() {
    return this.fileVersion;
  }

  public FileInfo setFileVersion(int fileVersion) {
    this.fileVersion = fileVersion;
    setFileVersionIsSet(true);
    return this;
  }

  public void unsetFileVersion() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FILEVERSION_ISSET_ID);
  }

  /** Returns true if field fileVersion is set (has been assigned a value) and false otherwise */
  public boolean isSetFileVersion() {
    return EncodingUtils.testBit(__isset_bitfield, __FILEVERSION_ISSET_ID);
  }

  public void setFileVersionIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FILEVERSION_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FILE_NAME:
      if (value == null) {
        unsetFileName();
      } else {
        setFileName((String)value);
      }
      break;

    case FILE_CONTENT:
      if (value == null) {
        unsetFileContent();
      } else {
        setFileContent((String)value);
      }
      break;

    case FILE_VERSION:
      if (value == null) {
        unsetFileVersion();
      } else {
        setFileVersion((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FILE_NAME:
      return getFileName();

    case FILE_CONTENT:
      return getFileContent();

    case FILE_VERSION:
      return getFileVersion();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FILE_NAME:
      return isSetFileName();
    case FILE_CONTENT:
      return isSetFileContent();
    case FILE_VERSION:
      return isSetFileVersion();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof FileInfo)
      return this.equals((FileInfo)that);
    return false;
  }

  public boolean equals(FileInfo that) {
    if (that == null)
      return false;

    boolean this_present_fileName = true && this.isSetFileName();
    boolean that_present_fileName = true && that.isSetFileName();
    if (this_present_fileName || that_present_fileName) {
      if (!(this_present_fileName && that_present_fileName))
        return false;
      if (!this.fileName.equals(that.fileName))
        return false;
    }

    boolean this_present_fileContent = true && this.isSetFileContent();
    boolean that_present_fileContent = true && that.isSetFileContent();
    if (this_present_fileContent || that_present_fileContent) {
      if (!(this_present_fileContent && that_present_fileContent))
        return false;
      if (!this.fileContent.equals(that.fileContent))
        return false;
    }

    boolean this_present_fileVersion = true;
    boolean that_present_fileVersion = true;
    if (this_present_fileVersion || that_present_fileVersion) {
      if (!(this_present_fileVersion && that_present_fileVersion))
        return false;
      if (this.fileVersion != that.fileVersion)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_fileName = true && (isSetFileName());
    list.add(present_fileName);
    if (present_fileName)
      list.add(fileName);

    boolean present_fileContent = true && (isSetFileContent());
    list.add(present_fileContent);
    if (present_fileContent)
      list.add(fileContent);

    boolean present_fileVersion = true;
    list.add(present_fileVersion);
    if (present_fileVersion)
      list.add(fileVersion);

    return list.hashCode();
  }

  @Override
  public int compareTo(FileInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetFileName()).compareTo(other.isSetFileName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileName, other.fileName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFileContent()).compareTo(other.isSetFileContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileContent, other.fileContent);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFileVersion()).compareTo(other.isSetFileVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFileVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.fileVersion, other.fileVersion);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("FileInfo(");
    boolean first = true;

    sb.append("fileName:");
    if (this.fileName == null) {
      sb.append("null");
    } else {
      sb.append(this.fileName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileContent:");
    if (this.fileContent == null) {
      sb.append("null");
    } else {
      sb.append(this.fileContent);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("fileVersion:");
    sb.append(this.fileVersion);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class FileInfoStandardSchemeFactory implements SchemeFactory {
    public FileInfoStandardScheme getScheme() {
      return new FileInfoStandardScheme();
    }
  }

  private static class FileInfoStandardScheme extends StandardScheme<FileInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, FileInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FILE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileName = iprot.readString();
              struct.setFileNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FILE_CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.fileContent = iprot.readString();
              struct.setFileContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FILE_VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.fileVersion = iprot.readI32();
              struct.setFileVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, FileInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.fileName != null) {
        oprot.writeFieldBegin(FILE_NAME_FIELD_DESC);
        oprot.writeString(struct.fileName);
        oprot.writeFieldEnd();
      }
      if (struct.fileContent != null) {
        oprot.writeFieldBegin(FILE_CONTENT_FIELD_DESC);
        oprot.writeString(struct.fileContent);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(FILE_VERSION_FIELD_DESC);
      oprot.writeI32(struct.fileVersion);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class FileInfoTupleSchemeFactory implements SchemeFactory {
    public FileInfoTupleScheme getScheme() {
      return new FileInfoTupleScheme();
    }
  }

  private static class FileInfoTupleScheme extends TupleScheme<FileInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, FileInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetFileName()) {
        optionals.set(0);
      }
      if (struct.isSetFileContent()) {
        optionals.set(1);
      }
      if (struct.isSetFileVersion()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetFileName()) {
        oprot.writeString(struct.fileName);
      }
      if (struct.isSetFileContent()) {
        oprot.writeString(struct.fileContent);
      }
      if (struct.isSetFileVersion()) {
        oprot.writeI32(struct.fileVersion);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, FileInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.fileName = iprot.readString();
        struct.setFileNameIsSet(true);
      }
      if (incoming.get(1)) {
        struct.fileContent = iprot.readString();
        struct.setFileContentIsSet(true);
      }
      if (incoming.get(2)) {
        struct.fileVersion = iprot.readI32();
        struct.setFileVersionIsSet(true);
      }
    }
  }

}

