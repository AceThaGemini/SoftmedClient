package org.ei.drishti.matcher;

import android.widget.Spinner;
import org.ei.drishti.adapter.AlertFilterCriterion;
import org.ei.drishti.domain.Alert;

public class MatchByVisitCode extends SpinnerMatcher {
    public MatchByVisitCode(Spinner spinner) {
        super(spinner);
    }

    public boolean matches(Alert alert) {
        String currentVisitCodePrefix = ((AlertFilterCriterion) currentValue()).visitCodePrefix();
        return alert.visitCode().startsWith(currentVisitCodePrefix);
    }
}
