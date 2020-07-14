package party.lemons.ass.util.fakeplayer;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;

/***
 Shamelessly stolen from carpet

 */
public class FakeNetworkManager extends ClientConnection
{
	public FakeNetworkManager(NetworkSide p)
	{
		super(p);
	}

	@Override
	public void disableAutoRead()
	{
	}

	@Override
	public void handleDisconnection()
	{
	}
}