import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField
import javafx.scene.text.TextAlignment
import tornadofx.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.Callable

class GUI3_FlightBooker : View("Flight Booker") {
    lateinit var flightType: ComboBox<String>
    lateinit var startDate: TextField
    lateinit var returnDate: TextField
    private lateinit var bookButton: Button

    private val validDates = SimpleBooleanProperty()
    private val parsedStart = SimpleObjectProperty<LocalDate?>()
    private val parsedReturn = SimpleObjectProperty<LocalDate?>()


    override val root = vbox {
        paddingAll = 10.0
        spacing = 10.0

        flightType = combobox <String> {
            listOf("one-way flight", "return flight").forEach { items.add(it) }
            value = "one-way flight"
            useMaxWidth = true
        }

        startDate = textfield {
            text = LocalDate.now().toString()
        }

        returnDate = textfield {
            text = LocalDate.now().toString()
        }

        bookButton = button {
            text = "Book"
            textAlignment = TextAlignment.CENTER
            useMaxWidth = true
        }
    }

    init {

        parsedStart.bind(startDate.textProperty().objectBinding(op = String?::asDate))
        parsedReturn.bind(returnDate.textProperty().objectBinding(op = String?::asDate))

        startDate.styleProperty().bind(parsedStart.stringBinding(op = {
            if (it == null) "-fx-background-color: lightcoral" else ""
        }))
        returnDate.styleProperty().bind(parsedReturn.stringBinding(op = {
            if (it == null) "-fx-background-color: lightcoral" else ""
        }))

        returnDate.disableProperty().bind(flightType.valueProperty().booleanBinding(op = {
            it != null && it == "one-way flight"
        }))

        validDates.bind(Bindings.createBooleanBinding(Callable <Boolean> {
            parsedStart.get() != null && parsedReturn.get() != null &&
                    parsedStart.get()!! <= parsedReturn.get()
        }, parsedStart, parsedReturn))

        bookButton.disableProperty().bind(validDates.not())

    }
}

val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
fun String?.asDate(): LocalDate? {
    return try {
        LocalDate.from(formatter.parse(this))
    } catch (e: Exception) {
        null
    }
}