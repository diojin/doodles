package outerhaven.cip.listing.six;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import outerhaven.cip.common.PhantomCode;

public class TravelQuotes {
	
	private class QuoteTask implements Callable<TravelQuote> {
	    private final TravelCompany company;
	    private final TravelInfo travelInfo;


		public TravelQuote call() throws Exception {
	        return company.solicitQuote(travelInfo);
	    }

		@PhantomCode
	    public QuoteTask(TravelCompany company2, TravelInfo travelInfo2) {
	    	this.company = company2;
	    	this.travelInfo = travelInfo2;
		}

		@PhantomCode
		public TravelQuote getTimeoutQuote(CancellationException e) {
			return null;
		}

		@PhantomCode
		public TravelQuote getFailureQuote(Throwable cause) {
			return null;
		}
	}

	public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo,
	        Set<TravelCompany> companies, Comparator<TravelQuote> ranking,
	        long time, TimeUnit unit) throws InterruptedException {
	    List<QuoteTask> tasks = new ArrayList<QuoteTask>();
	    for (TravelCompany company : companies)
	        tasks.add(new QuoteTask(company, travelInfo));
	    List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);
	    List<TravelQuote> quotes = new ArrayList<TravelQuote>(tasks.size());
	    Iterator<QuoteTask> taskIter = tasks.iterator();
	    for (Future<TravelQuote> f : futures) {
	        QuoteTask task = taskIter.next();
	        try {
	            quotes.add(f.get());
	        } catch (ExecutionException e) {
	            quotes.add(task.getFailureQuote(e.getCause()));
	        } catch (CancellationException e) {
	            quotes.add(task.getTimeoutQuote(e));
	        }
	    }
	    Collections.sort(quotes, ranking);
	    return quotes;
	}
	
	@PhantomCode
	private ExecutorService exec;
	
	@PhantomCode
	private class TravelQuote{
		
	}
	
	@PhantomCode
	private class TravelCompany{
		public TravelQuote solicitQuote(TravelInfo travelInfo){
			return null;
		}
	}
	
	@PhantomCode
	private class TravelInfo{
		
	}

}
