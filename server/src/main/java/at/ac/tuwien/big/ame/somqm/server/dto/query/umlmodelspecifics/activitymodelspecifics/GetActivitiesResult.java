package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import java.util.List;
import java.util.Objects;

public class GetActivitiesResult {

  private final List<Activity> activities;

  public GetActivitiesResult(List<Activity> activities) throws IllegalArgumentException {
    if (activities == null) {
      throw new IllegalArgumentException();
    }
    this.activities = activities;
  }

  public List<Activity> getActivities() {
    return activities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetActivitiesResult that = (GetActivitiesResult) o;
    return activities.equals(that.activities);
  }

  @Override
  public int hashCode() {
    return Objects.hash(activities);
  }

}
