import java.io.IOException;
import java.util.List;

public interface BackendInterface
{
	public void readData(String filename) throws IOException;
	
	
	public List<String> getRange(int low, int high);
	
	
	public List<String> getYear(int year);
	
	
	public List<String> getEnergyRange(int low, int high);
	
}