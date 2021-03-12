//import { CallbackID } from '@capacitor/core';

declare module '@capacitor/core' {
  interface PluginRegistry {
    CapacitorJobScheduler: CapacitorJobSchedulerPlugin;
  }
}

export interface CallbackError extends Error {
  code?: string;
}

export interface CapacitorJobSchedulerPlugin {
  echo(options: {
    value: string;
    contentTitle: string;
    contentText: string;
  }): Promise<{ value: string; contentTitle: string; contentText: string }>;
  /* startForegroundServiceAboveOreo(
    ContentTitle: string,
    ContentText: string,
  ): CallbackID; */
  cancelJob(): Promise<void>;
}
