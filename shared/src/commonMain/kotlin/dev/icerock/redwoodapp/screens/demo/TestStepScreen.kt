package dev.icerock.redwoodapp.screens.demo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import app.cash.redwood.LayoutModifier
import app.cash.redwood.layout.api.Constraint
import app.cash.redwood.layout.api.CrossAxisAlignment
import app.cash.redwood.layout.api.MainAxisAlignment
import app.cash.redwood.layout.api.Overflow
import app.cash.redwood.layout.api.Padding
import app.cash.redwood.layout.compose.Column
import app.cash.redwood.layout.compose.Row
import dev.icerock.moko.graphics.Color
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.desc.desc
import dev.icerock.redwood.schema.ButtonType
import dev.icerock.redwood.schema.Size
import dev.icerock.redwood.schema.TextType
import dev.icerock.redwood.schema.compose.Button
import dev.icerock.redwood.schema.compose.Card
import dev.icerock.redwood.schema.compose.Image
import dev.icerock.redwood.schema.compose.Space
import dev.icerock.redwood.schema.compose.Stack
import dev.icerock.redwood.schema.compose.Text
import dev.icerock.redwoodapp.SimpleLoginViewModel
import dev.icerock.redwoodapp.ToolabrArgs
import dev.icerock.redwoodapp.ViewModelOwner
import dev.icerock.redwoodapp.getViewModel
import dev.icerock.redwoodapp.navigation.Navigator
import dev.icerock.redwoodapp.navigation.ScreenSettings
import kotlinx.coroutines.flow.MutableStateFlow
import dev.icerock.redwoodapp.screens.demo.Mock
import dev.icerock.redwoodapp.screens.demo.navigation.Screens
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.example.library.MR

@Composable
fun TestStepScreen(
    navigator: Navigator,
    step: Int,
    screenSettings: ScreenSettings<ToolabrArgs>,
    viewModelOwner: ViewModelOwner
) {
    val viewModel: TestStepViewModel = getViewModel(viewModelOwner) {
        TestStepViewModel(step)
    }
    val title by viewModel.title.collectAsState()
    LaunchedEffect(screenSettings) {
        viewModel.setStep(step)
    }
    LaunchedEffect(screenSettings, title) {
        screenSettings.setToolbarData(ToolabrArgs.Simple(title.desc()))
    }
    val questions by viewModel.questions.collectAsState()
    val answers by viewModel.answers.collectAsState()

    Stack(
        child1 = {
            Column(
                overflow = Overflow.Scroll,
                horizontalAlignment = CrossAxisAlignment.Start
            ) {
                questions.forEachIndexed { questIndex, question ->
                    Row {
                        Text(
                            question.qustion,
                            textType = TextType.Bold, layoutModifier = LayoutModifier.padding(
                                Padding(
                                    horizontal = 12,
                                    vertical = 6
                                )
                            ),
                            width = Size.Fill
                        )
                    }

                    question.answers.forEachIndexed { answerIndex, answer ->


                        Button(
                            text = answer.desc(),
                            buttonType = ButtonType.Text,
                            icon = if (answers.get(questIndex) == answerIndex) MR.images.radioClicked else MR.images.radio,
                            width = Size.Wrap,
                            onClick = { viewModel.setAnswer(questIndex, answerIndex) }
                        )
                    }
                }
                Column {
                   Text("   ", layoutModifier = LayoutModifier.padding(Padding(32)))
                }

                Column {
                    Text("   ", layoutModifier = LayoutModifier.padding(Padding(32)))
                }
            }

        },
        child2 = {
            Column(verticalAlignment = MainAxisAlignment.End) {
                Button(
                    layoutModifier = LayoutModifier.padding(Padding(16)),
                    text = ("Далее").desc(),
                    buttonType = ButtonType.Primary,
                    enabled = questions.size == answers.size,
                    width = Size.Fill,
                    onClick = {
                        if (viewModel.testDetails.value.steps.size == step + 1) {
                            navigator.navigate(Screens.TEST_FINAL)
                        } else {
                            navigator.navigate(Screens.testStep(step + 1))
                        }
                    }
                )
            }
        }
    )
}

class TestStepViewModel(step: Int) : ViewModel() {
    val _step = MutableStateFlow(step)
    val testDetails = MutableStateFlow(Mock.FIRT_TEST)
    val testStep = testDetails.combine(_step) { test, step -> test.steps[step] }
    val title = testStep.map { it.title }.stateIn(viewModelScope, SharingStarted.Lazily, "")
    val questions =
        testStep.map { it.questions }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())
    val answers = MutableStateFlow<Map<Int, Int>>(mapOf())

    fun setStep(step: Int) {
        _step.value = step
        answers.value = mapOf()
    }

    fun setAnswer(question: Int, answer: Int) {
        val map = mutableMapOf<Int, Int>()
        map.putAll(answers.value)
        map.put(question, answer)
        answers.value = map
    }
}

data class TestDetails(
    val id: Int,
    val steps: List<TestStep>
)

data class TestStep(
    val id: Int,
    val title: String,
    val questions: List<TestQuestion>
)

data class TestQuestion(
    val qustion: String,
    val answers: List<String>
)


