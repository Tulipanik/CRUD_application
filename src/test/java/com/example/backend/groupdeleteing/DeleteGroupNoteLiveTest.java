package com.example.backend.groupdeleteing;

import com.example.backend.groupdeleteing.DeleteGroupNoteSteps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.TXT;

public class DeleteGroupNoteLiveTest extends JUnitStories {

  @Override
  public Configuration configuration() {
    return new MostUsefulConfiguration()
        .useStoryLoader(new LoadFromClasspath(this.getClass()))
        .useStoryReporterBuilder(new StoryReporterBuilder()
            .withCodeLocation(codeLocationFromClass(this.getClass()))
            .withFormats(CONSOLE, TXT, HTML));
  }

  @Override
  public InjectableStepsFactory stepsFactory() {
    return new InstanceStepsFactory(configuration(), new DeleteGroupNoteSteps());
  }

  @Override
  public List<String> storyPaths() {
    return List.of("stories/DeleteNoteGroup.story");
  }
}
