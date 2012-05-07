package org.ei.drishti.repository;

import com.xtremelabs.robolectric.RobolectricTestRunner;
import org.ei.drishti.domain.Action;
import org.ei.drishti.domain.Alert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.ActionBuilder.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(RobolectricTestRunner.class)
public class AllAlertsTest {
    @Mock
    private AlertRepository alertRepository;
    private AllAlerts allAlerts;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        allAlerts = new AllAlerts(alertRepository);
    }

    @Test
    public void shouldUpdateAlertRepositoryForCreateAlertActions() throws Exception {
        Action firstAction = actionForCreate("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1");
        Action secondAction = actionForCreate("Case Y", "late", "Theresa 2", "ANC 2", "Thaayi 2");

        allAlerts.saveNewAlerts(Arrays.asList(firstAction, secondAction));

        verify(alertRepository).update(firstAction);
        verify(alertRepository).update(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllAlerts() throws Exception {
        Action firstAction = actionForCreate("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1");
        Action secondAction = actionForCreate("Case Y", "late", "Theresa 2", "ANC 2", "Thaayi 2");

        allAlerts.saveNewAlerts(Arrays.asList(firstAction, secondAction));
        allAlerts.deleteAllAlerts();

        verify(alertRepository).deleteAllAlerts();
    }

    @Test
    public void shouldDeleteFromRepositoryForDeleteActions() throws Exception {
        Action firstAction = actionForDelete("Case X", "ANC 1");
        Action secondAction = actionForDelete("Case Y", "ANC 2");

        allAlerts.saveNewAlerts(Arrays.asList(firstAction, secondAction));

        verify(alertRepository).delete(firstAction);
        verify(alertRepository).delete(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldDeleteAllFromRepositoryForDeleteAllActions() throws Exception {
        Action firstAction = actionForDeleteAll("Case X");
        Action secondAction = actionForDeleteAll("Case Y");

        allAlerts.saveNewAlerts(Arrays.asList(firstAction, secondAction));

        verify(alertRepository).deleteAll(firstAction);
        verify(alertRepository).deleteAll(secondAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldNotFailIfActionTypeIsNotExpected() throws Exception {
        allAlerts.saveNewAlerts(Arrays.asList(new Action("Case X", "UNKNOWN-TYPE", new HashMap<String, String>(), "0")));
    }

    @Test
    public void shouldUpdateDeleteAndDeleteAllAlertActionsBasedOnTheirType() throws Exception {
        Action firstCreateAction = actionForCreate("Case X", "due", "Theresa 1", "ANC 1", "Thaayi 1");
        Action firstDeleteAction = actionForDelete("Case Y", "ANC 2");
        Action secondCreateAction = actionForCreate("Case Z", "due", "Theresa 2", "ANC 2", "Thaayi 2");
        Action deleteAllAction = actionForDeleteAll("Case A");
        Action secondDeleteAction = actionForDelete("Case B", "ANC 3");

        allAlerts.saveNewAlerts(Arrays.asList(firstCreateAction, firstDeleteAction, secondCreateAction, deleteAllAction, secondDeleteAction));

        InOrder inOrder = inOrder(alertRepository);
        inOrder.verify(alertRepository).update(firstCreateAction);
        inOrder.verify(alertRepository).delete(firstDeleteAction);
        inOrder.verify(alertRepository).update(secondCreateAction);
        inOrder.verify(alertRepository).deleteAll(deleteAllAction);
        inOrder.verify(alertRepository).delete(secondDeleteAction);
        verifyNoMoreInteractions(alertRepository);
    }

    @Test
    public void shouldFetchAllAlertsFromRepository() throws Exception {
        List<Alert> expectedAlerts = Arrays.asList(new Alert("Case X", "Theresa 1", "ANC 1", "Thaayi 1", 1, "2012-01-01"), new Alert("Case Y", "Theresa 2", "ANC 2", "Thaayi 2", 1, "2012-01-01"));
        when(alertRepository.allAlerts()).thenReturn(expectedAlerts);

        List<Alert> alerts = allAlerts.fetchAlerts();

        assertEquals(expectedAlerts, alerts);
    }
}