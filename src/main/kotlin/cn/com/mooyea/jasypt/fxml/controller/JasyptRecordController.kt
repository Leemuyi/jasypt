package cn.com.mooyea.jasypt.fxml.controller

import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.fxml.entity.JasyptRecordEntity
import cn.com.mooyea.jasypt.fxml.service.IJasyptRecordService
import de.felixroske.jfxsupport.FXMLController
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory
import java.net.URL
import java.util.*
import javax.annotation.Resource

/**
 * <h1>JasyptRecordController<h1>
 *
 * Copyright (C), 星期一,06,11月,2023
 * <br></br>
 * <hr></hr>
 * <h3>File Info:</h3>
 *
 * FileName: JasyptRecordController
 *
 * Author:   mooye
 *
 * Work_Email： lidy@skyvis.com.cn
 *
 * E-mail： mooyeali@yeah.net
 *
 * Date:     2023/11/6
 *
 * Description: 加密历史记录控制器层
 * <hr></hr>
 * <h3>History:</h3>
 * <hr></hr>
 * <table>
 * <thead>
 * <tr><td style='width:100px;' center>Author</td><td style='width:200px;' center>Time</td><td style='width:100px;' center>Version_Number</td><td style='width:100px;' center>Description</td></tr>
</thead> *
 * <tbody>
 * <tr><td style='width:100px;' center>mooye</td><td style='width:200px;' center>09:48 2023/11/6</td><td style='width:100px;' center>v_1.0.0</td><td style='width:100px;' center>创建</td></tr>
</tbody> *
</table> *
 * <hr></hr>
 * <br></br>
 *
 * @author mooye
</h1></h1> */
@FXMLController
class JasyptRecordController: Initializable {

    @Resource
    lateinit var service: IJasyptRecordService
    @FXML
    private lateinit var saltText: TextField

    @FXML
    private lateinit var clearText: TextField

    @FXML
    private lateinit var algorithmChoice: ChoiceBox<String>

    @FXML
    lateinit var recordTable: TableView<JasyptRecordEntity>

    @FXML
    lateinit var clearTextColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    lateinit var saltColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    lateinit var algorithmColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    lateinit var ciphertextColumn: TableColumn<JasyptRecordEntity, String>

    /**
     * 查询按钮点击事件
     *
     */
    @FXML
    private fun query() {
        log.info("查询")
        if(this::service.isInitialized) {
            log.info("初始化完成")
            log.info("{},{},{}",clearText.text,
                saltText.text,
                algorithmChoice.value)
            renderingData(
                FXCollections.observableArrayList(
                    service.queryRecord(
                        clearText.text,
                        saltText.text,
                        algorithmChoice.value
                    )
                )
            )
        }
    }

    /**
     * 重置按钮点击事件
     *
     */
    @FXML
    private fun reset() {
        saltText.text = ""
        clearText.text = ""
        algorithmChoice.value = ""
        loadRecord()
    }


    fun loadRecord() {
        if (this::service.isInitialized) {
            renderingData(
                FXCollections.observableList(service.list()
                )
            )
        }
    }

    private fun renderingData(data: ObservableList<JasyptRecordEntity>){
        if (this::clearTextColumn.isInitialized) {
            clearTextColumn.cellValueFactory = PropertyValueFactory("cleartext")
        }
        if (this::saltColumn.isInitialized) {
            saltColumn.cellValueFactory = PropertyValueFactory("salt")
        }
        if (this::algorithmColumn.isInitialized) {
            algorithmColumn.cellValueFactory = PropertyValueFactory("algorithm")
        }
        if (this::ciphertextColumn.isInitialized) {
            ciphertextColumn.cellValueFactory = PropertyValueFactory("encrypt")
        }
        if (this::recordTable.isInitialized) {
            recordTable.items = data
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        log.info("初始化")
        loadRecord()
    }
}
